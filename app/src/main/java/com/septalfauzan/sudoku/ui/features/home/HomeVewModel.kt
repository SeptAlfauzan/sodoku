package com.septalfauzan.sudoku.ui.features.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.septalfauzan.sudoku.core.domain.SudokuBoxCell
import com.septalfauzan.sudoku.core.domain.usecase.SudokuGameUseCaseInterface
import com.septalfauzan.sudoku.helper.DataMapper.toSudokuBoxCellStateList
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
            _boardState.addAll(this.toSudokuBoxCellStateList())
            _boardSolutionState = boardSolution
        } catch (e: Exception) {
            Timber.tag("TAG").d("getBoardSolution: ${e.message}")
            throw e
        }
    }

    private fun initGame() {
        _loadingBoard.value = true
        viewModelScope.launch(Dispatchers.Default) {
            try {
                getBoard().getBoardSolution()
                Timber.tag("TAG").d("initGame: $_boardState")
            } catch (e: Exception) {
                throw e
            } finally {
                delay(1500)
                _loadingBoard.value = false
            }
        }
    }
}