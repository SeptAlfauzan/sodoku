package com.septalfauzan.sodoku.core.data

import com.septalfauzan.sodoku.core.data.local.LoadLocalJson
import com.septalfauzan.sodoku.core.domain.SudokuRepositoryInterface
import com.septalfauzan.sodoku.helper.DataMapper.toDeepMutableList
import com.septalfauzan.sodoku.helper.Sudoku
import javax.inject.Inject


class SudokuRepository @Inject constructor() :
    SudokuRepositoryInterface {
    override fun getPlainBoard(jsonStr: String): List<List<Int>> = LoadLocalJson.getBoard(jsonStr).board
    override fun getGameReadyBoard(): List<List<Int>> {
        val sudoku = Sudoku(9)
        return sudoku.generateRandomSeed(3)
    }

    override fun getBoardGameSollution(board: List<List<Int>>): List<List<Int>> {
        val result = board.toDeepMutableList()
        val sudoku = Sudoku(gridSize = 9)
        val solvable = sudoku.solveBoard(result)
        if (!solvable) throw Exception("Board is unsolvable!")

        return result
    }

    override fun compareBoardsCell(
        value: Int,
        solutionBoard: List<List<Int>>,
        row: Int,
        col: Int
    ): Boolean = Sudoku(9).compareCellValueIgnoreZero(value, solutionBoard, row, col)
}