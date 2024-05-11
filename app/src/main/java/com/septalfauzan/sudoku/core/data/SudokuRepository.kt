package com.septalfauzan.sudoku.core.data

import com.septalfauzan.sudoku.core.data.local.LoadLocalJson
import com.septalfauzan.sudoku.core.domain.SudokuRepositoryInterface
import com.septalfauzan.sudoku.helper.DataMapper.toDeepMutableList
import com.septalfauzan.sudoku.helper.Sudoku
import javax.inject.Inject


class SudokuRepository @Inject constructor() :
    SudokuRepositoryInterface {
    override fun getPlainBoard(jsonStr: String): List<List<Int>> = LoadLocalJson.getBoard(jsonStr).board
    override fun getGameReadyBoard(): List<List<Int>> {
        val sudoku = Sudoku(9)
        val board = sudoku.generateRandomSeed(8).toDeepMutableList()
        sudoku.solveBoard(board)
        return sudoku.getGameReadyBoard(board, 6)
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