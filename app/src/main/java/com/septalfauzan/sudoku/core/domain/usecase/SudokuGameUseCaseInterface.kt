package com.septalfauzan.sudoku.core.domain.usecase

interface SudokuGameUseCaseInterface {
    suspend fun getBoard(): List<List<Int>>

    suspend fun getBoardSolution(board: List<List<Int>>): List<List<Int>>
    fun compareBoardCell(value: Int, solutionBoard: List<List<Int>>, row: Int, col: Int): Boolean
}