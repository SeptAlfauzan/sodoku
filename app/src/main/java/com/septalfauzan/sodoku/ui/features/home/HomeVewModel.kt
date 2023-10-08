package com.septalfauzan.sodoku.ui.features.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.septalfauzan.sodoku.core.domain.SudokuBoxCell
import com.septalfauzan.sodoku.core.domain.usecase.SudokuGameUseCaseInterface
import com.septalfauzan.sodoku.helper.DataMapper.toList
import com.septalfauzan.sodoku.helper.DataMapper.toSudokuBoxCellStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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

    init {
        initGame()
    }

    fun setSelectedCell(row: Int?, col: Int?) {
        _selectedRow.value = row
        _selectedColumn.value = col
    }

    fun updateBoard(number: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (selectedRow.value != null && selectedColumn.value != null) {

                    val isValid = useCase.compareBoardCell(
                        number,
                        _boardSolutionState,
                        selectedRow.value!!,
                        selectedColumn.value!!
                    )

                    _boardState[selectedRow.value!!][selectedColumn.value!!] =
                        SudokuBoxCell(value = number, isValid = isValid)
                }
            } catch (e: Exception) {
                Log.d("Error", "updateBoard: ${e.message}")
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
            Log.d("TAG", "getBoardSolution: ${e.message}")
            throw e
        }
    }

    private fun initGame() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                getBoard().getBoardSolution()
                Log.d("TAG", "initGame: $_boardState")
            } catch (e: Exception) {
                throw e
            } finally {
                _loadingBoard.value = false
            }
        }
    }
}