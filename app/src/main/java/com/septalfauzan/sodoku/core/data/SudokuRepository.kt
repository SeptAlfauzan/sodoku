package com.septalfauzan.sodoku.core.data

import android.content.Context
import com.septalfauzan.sodoku.core.data.local.LoadLocalJson
import com.septalfauzan.sodoku.core.domain.SudokuBox
import com.septalfauzan.sodoku.core.domain.SudokuRepositoryInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class SudokuRepository @Inject constructor() :
    SudokuRepositoryInterface {
    override fun getBoard(jsonStr: String): List<List<Int>> = LoadLocalJson.getBoard(jsonStr).board
}

//val board = LoadLocalJson.getBoard(context).board
//val result = mutableListOf<SudokuBox>()
//board.mapIndexed {rowIndex, i ->
//    i.mapIndexed { colIndex, j ->
//        result.add(SudokuBox(value = j, row = rowIndex, col = colIndex))
//    }
//}