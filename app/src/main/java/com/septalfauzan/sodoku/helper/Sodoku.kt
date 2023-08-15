package com.septalfauzan.sodoku.helper

import kotlin.math.floor
import kotlin.random.Random

object Sodoku {
    fun randomGenerator(listAvailableNum: List<Int>): Int {
        if (listAvailableNum.size == 1) return listAvailableNum[0]
        val randomIndex =
            Random.nextInt(0, listAvailableNum.size)
        return listAvailableNum[randomIndex]
    }

    fun checkIsValid(checkedNum: Int, numbersInBoard: List<Int>): Boolean {
        if (checkedNum < 1 || checkedNum > 9) return false
        if (numbersInBoard.contains(checkedNum)) return false
        return true
    }

    fun generateAvailableBoard(filledBoard: List<Int>): List<Int> {
        val list = (1..9).map { it }.toMutableList()
        list.removeAll(filledBoard.toSet())
        return list
    }

    fun filledSubBoard(
        availableNumber: MutableList<Int> = (1..9).map { it }.toMutableList(),
        board: MutableList<Int> = mutableListOf()
    ): List<Int> {
        if (board.size >= 9) return board

        val number = randomGenerator(availableNumber)
        board.add(number)
        availableNumber.remove(number)
        return filledSubBoard(availableNumber, board)
    }

    fun generateBoard(filledBoard: List<List<Int>>) {
        var starting = 0

    }

    fun checkRowContainSameNum(
        board: List<List<Int>>,
        number: Int,
        subBoardIndex: Int,
        rowInSubBoard: Int
    ): Boolean {
        if (rowInSubBoard > 2 || rowInSubBoard < 0) throw Exception("row value must be between 0 to 2")
        val result: MutableList<List<Int>> = mutableListOf()
        val boardRow = floor(subBoardIndex / 3f).toInt()

        val minBoard = subBoardIndex * 3
        val maxBoard = minBoard + 3
        val selectedBoardRange = boardRow until maxBoard

        val minRow = rowInSubBoard * 3
        val maxRow = minRow + 3
        val selectedSubBoard = minRow until maxRow

        for (i in selectedBoardRange) {
            val row = board[i].filterIndexed { index, _ -> index in selectedSubBoard }
            result.add(row)
        }
        val numberInRow = result.flatten().toSet()
        println(numberInRow)
        return !numberInRow.contains(number)
    }

    fun checkColumnContainSameNum(
        board: List<List<Int>>,
        number: Int,
        subBoardIndex: Int,
        columnInSubBoard: Int
    ): Boolean {
        if (columnInSubBoard > 2 || columnInSubBoard < 0) throw Exception("row value must be between 0 to 2")
        val result: MutableList<List<Int>> = mutableListOf()

        val columnIndexInBoard = subBoardIndex % 3
        val selectedSubBoardIndex = (0..8).map { it }.filter {
            it % 3 == columnIndexInBoard
        }

        val columnIndexInSubBoard = columnInSubBoard % 3

        for (i in selectedSubBoardIndex) {
            val row = board[i].filterIndexed { index, _ -> index % 3 == columnIndexInSubBoard }
            result.add(row)
        }
        val numberInColumn = result.flatten().toSet()
        println(numberInColumn)

        return !numberInColumn.contains(number)
    }

    fun generateRandomSeed() {

    }
}