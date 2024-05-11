package com.septalfauzan.sudoku.ui.features.home

import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.septalfauzan.sudoku.core.domain.SudokuBoxCell
import com.septalfauzan.sudoku.core.domain.usecase.SudokuGameUseCaseInterface
import com.septalfauzan.sudoku.helper.DataMapper.toSudokuBoxCellStateList
import com.septalfauzan.sudoku.ui.common.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class HomeVewModel @Inject constructor(private val useCase: SudokuGameUseCaseInterface) :
    ViewModel() {
    private val _boardState = mutableStateListOf<MutableList<SudokuBoxCell>>()
    val boardState: List<List<SudokuBoxCell>> = _boardState

    private var _boardSolutionState = listOf<List<Int>>()

    private val _selectedRow: MutableStateFlow<Int?> = MutableStateFlow(null)
    val selectedRow: StateFlow<Int?> = _selectedRow

    private val _selectedColumn: MutableStateFlow<Int?> = MutableStateFlow(null)
    val selectedColumn: StateFlow<Int?> = _selectedColumn

    private val _loadingBoard: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val loadingBoard: StateFlow<Boolean> = _loadingBoard

    val initialGameLife = 3
    private val _gameLife: MutableStateFlow<Int> = MutableStateFlow(initialGameLife)
    val gameLife: StateFlow<Int> = _gameLife

    private val initialCountDownTimer = 120
    private val _countDown: MutableStateFlow<Int> = MutableStateFlow(initialCountDownTimer)
    val countDownTimer: StateFlow<Int> = _countDown

    private val _gameState: MutableStateFlow<GameState> = MutableStateFlow(GameState.NOT_STARTED)
    val gameState: StateFlow<GameState> = _gameState

    private val timer = object: CountDownTimer((initialCountDownTimer * 1000).toLong(), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            _countDown.value -= 1
        }
        override fun onFinish() {
            _countDown.value = 0
            _gameState.value = GameState.GAME_OVER
        }
    }


    init {
        initGame()
    }

    fun setSelectedCell(row: Int?, col: Int?) {
        _selectedRow.value = row
        _selectedColumn.value = col
    }

    fun updateBoard(number: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                if (selectedRow.value != null && selectedColumn.value != null) {

                    val isValid = useCase.compareBoardCell(
                        number,
                        _boardSolutionState,
                        selectedRow.value!!,
                        selectedColumn.value!!
                    )

                    if(!isValid){
                        _gameLife.value -= 1
                        if(_gameLife.value == 0) {
                            _gameState.value = GameState.GAME_OVER
                            timer.cancel()
                        }
                    }

                    _boardState[selectedRow.value!!][selectedColumn.value!!] =
                        SudokuBoxCell(value = number, isValid = isValid)
                }
            } catch (e: Exception) {
                Timber.tag("Error").e("updateBoard: ${e.message}")
            }
        }
    }

    private suspend fun getBoard(): List<List<Int>> {
        try {
            return useCase.getBoard()
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun List<List<Int>>.getBoardSolution() {
        try {
            val boardSolution = useCase.getBoardSolution(this)
            Timber.tag("BOARD").d("$boardSolution")
            Timber.tag("BOARD").d("unfinished $this")


            _boardState.clear()
            _boardState.addAll(this.toSudokuBoxCellStateList())
            _boardSolutionState = boardSolution
        } catch (e: Exception) {
            Timber.tag("TAG").d("getBoardSolution: ${e.message}")
            throw e
        }
    }

    fun initGame() {

        _loadingBoard.value = true
        _gameState.value = GameState.ON_GAME
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getBoard().getBoardSolution()
                Timber.tag("TAG").d("initGame: $_boardState")
            } catch (e: Exception) {
                print(e)
                throw e
            } finally {
                delay(1500)
                _gameLife.value = initialGameLife
                _countDown.value = initialCountDownTimer
                _loadingBoard.value = false
                timer.start()
            }
        }
    }
}