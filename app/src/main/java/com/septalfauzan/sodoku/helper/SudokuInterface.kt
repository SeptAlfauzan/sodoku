package com.septalfauzan.sodoku.helper

abstract class SudokuInterface {
    abstract val gridSize: Int
    abstract fun isInRow(board: List<List<Int>>, row: Int, number: Int): Boolean
    abstract fun isInColumn(board: List<List<Int>>, column: Int, number: Int): Boolean
    abstract fun isInBoard(board: List<List<Int>>, row: Int, column: Int, number: Int): Boolean
    abstract fun isValidPlacement(board: List<List<Int>>, row: Int, column: Int, number: Int): Boolean
    abstract fun solveBoard(board: MutableList<MutableList<Int>>): Boolean
    abstract fun emptiedBoard(board: MutableList<MutableList<Int>>, level: Int): List<List<Int>>
    abstract fun generateRandomSeed(cellsOccupy: Int = 1) : List<List<Int>>
    abstract fun compareCellValueIgnoreZero(board: List<List<Int>>, solutionBoard:List<List<Int>>, row: Int, col: Int) : Boolean
    abstract fun compareCellValueIgnoreZero(value: Int, solutionBoard:List<List<Int>>, row: Int, col: Int) : Boolean
    abstract fun printBoard(board: List<List<Int>>)
}