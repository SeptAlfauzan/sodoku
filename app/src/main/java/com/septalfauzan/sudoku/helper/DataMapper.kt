package com.septalfauzan.sudoku.helper

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.septalfauzan.sudoku.core.domain.SudokuBoxCell

object DataMapper {
    fun List<List<Int>>.toDeepMutableList(): MutableList<MutableList<Int>> =
        this.map { it.toMutableList() }.toMutableList()

    fun List<List<Int>>.toInnerMutableStateList(): List<SnapshotStateList<Int>> = this.map {row ->
        val stateList = mutableStateListOf<Int>()
        row.map { col ->
            stateList.add(col)
        }
        stateList
    }

    fun List<List<Int>>.toSudokuBoxCellStateList():  List<SnapshotStateList<SudokuBoxCell>> = this.map {row ->
        val stateList = mutableStateListOf<SudokuBoxCell>()
        row.map { col ->
            val item = SudokuBoxCell(value = col, isValid = if(col == 0) null else true)
            stateList.add(item)
        }
        stateList
    }

    fun List<List<SudokuBoxCell>>.toList():  List<List<Int>> = this.map {row ->
        row.map { col ->
            col.value
        }
    }

}