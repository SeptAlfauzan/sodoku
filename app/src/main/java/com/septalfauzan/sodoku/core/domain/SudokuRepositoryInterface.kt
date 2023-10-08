package com.septalfauzan.sodoku.core.domain

interface SudokuRepositoryInterface {
    fun getPlainBoard(jsonStr: String): List<List<Int>>
    fun getGameReadyBoard(): List<List<Int>>

    fun getBoardGameSollution(board: List<List<Int>>): List<List<Int>>

    fun compareBoardsCell(value: Int, solutionBoard: List<List<Int>>, row: Int, col: Int): Boolean
}