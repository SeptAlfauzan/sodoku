package com.septalfauzan.sodoku.ui.features.home

import android.content.Context
import android.util.Log
import android.util.SparseArray
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.septalfauzan.sodoku.R
import com.septalfauzan.sodoku.core.domain.usecase.SudokuGameUseCaseInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVewModel @Inject constructor(@ApplicationContext private val context: Context, private val useCase: SudokuGameUseCaseInterface) : ViewModel() {
    private val _boardState = mutableStateListOf<MutableList<Int>>()
    val boardState: List<List<Int>> = _boardState

    private val _selectedRow: MutableStateFlow<Int?> = MutableStateFlow(null)
    val selectedRow: StateFlow<Int?> = _selectedRow

    private val _selectedColumn: MutableStateFlow<Int?> = MutableStateFlow(null)
    val selectedColumn: StateFlow<Int?> = _selectedColumn

    private val _number: MutableStateFlow<Int?> = MutableStateFlow(null)
    val number: StateFlow<Int?> = _number
    init {
        getBoard()
    }

    fun updateNumber(number: Int){
        viewModelScope.launch {
            _number.value = number
        }
    }

    fun setSelectedCell(row: Int?, col: Int?) {
        _selectedRow.value = row
        _selectedColumn.value = col
    }

    fun updateBoard(number: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            if (selectedRow.value != null && selectedColumn.value != null) {
                _boardState[selectedRow.value!!][selectedColumn.value!!] = number
            }
        }
    }

    private fun getBoard() {
        viewModelScope.launch(Dispatchers.Default) {
            val jsonStr = context.resources.openRawResource(R.raw.empty_board).bufferedReader().use { it.readText() }
            val board = useCase.getBoard(jsonStr)
            board.map { row ->
                val columnList = mutableStateListOf<Int>()
                row.map { col ->
                    columnList.add(col)
                }
                _boardState.add(columnList)
            }
        }
    }
}