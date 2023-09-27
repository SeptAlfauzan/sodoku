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
        parentBoard: List<List<Int>>,
        currentSubBoardIndex: Int,
        board: MutableList<Int> = mutableListOf(),
//        availableNumber: MutableList<Int> = (1..9).map { it }.toMutableList(),
    ): List<Int> {
        if (board.size >= 9) return board

        val columnIndexInBoard = board.size % 3
        val rowIndexInBoard = floor(board.size / 3f).toInt()


        val possibleNumber = when (currentSubBoardIndex) {
            0 -> (1..9).map { it }.toSet().subtract(board.toSet())
            else -> getPossibleNumbersFromRowAndColumn(
                board = parentBoard,
                subBoardIndex = currentSubBoardIndex,
                rowInSubBoard = rowIndexInBoard,
                columnInSubBoard = columnIndexInBoard
            )
        }
        println("${possibleNumber} board $board")
        val number = randomGenerator(possibleNumber.toList())

        board.add(number)

        return filledSubBoard(
            board = board,
            parentBoard = parentBoard,
            currentSubBoardIndex = currentSubBoardIndex
        )
    }

    fun checkRowContainSameNum(
        board: List<List<Int>>,
        number: Int,
        subBoardIndex: Int,
        rowInSubBoard: Int
    ): Boolean {
        if (rowInSubBoard > 2 || rowInSubBoard < 0) throw Exception("row value must be between 0 to 2")

        if (board.isEmpty()) return true

        val result: MutableList<List<Int>> = mutableListOf()
        val boardRow = floor(subBoardIndex / 3f).toInt()

        val minBoard = boardRow * 3
        val maxBoard = minBoard + 3
        val selectedBoardRange = minBoard until maxBoard

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
        if (columnInSubBoard > 2 || columnInSubBoard < 0) throw Exception("col value must be between 0 to 2")

        if (board.isEmpty()) return true

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


    fun numInSameRow(
        board: List<List<Int>>,
        subBoardIndex: Int,
        rowInSubBoard: Int
    ): Set<Int> {
        if (rowInSubBoard > 2 || rowInSubBoard < 0) throw Exception("row value must be between 0 to 2")

        if (board.isEmpty()) return setOf()

        val result: MutableList<List<Int>> = mutableListOf()
        val boardRow = floor(subBoardIndex / 3f).toInt()

        val minBoard = boardRow * 3
        val maxBoard = (minBoard + (subBoardIndex % 3))
        val selectedBoardRange = minBoard until maxBoard

        val minRow = rowInSubBoard * 3
        val maxRow = minRow + 3
        val selectedSubBoard = minRow until maxRow

        for (i in selectedBoardRange) {
            val row = board[i].filterIndexed { index, _ -> index in selectedSubBoard }
            result.add(row)
        }
        return result.flatten().toSet()
    }

    fun numsInSameColumn(
        board: List<List<Int>>,
        subBoardIndex: Int,
        columnInSubBoard: Int
    ): Set<Int> {
        if (columnInSubBoard > 2 || columnInSubBoard < 0) throw Exception("col value must be between 0 to 2")

        if (board.isEmpty()) return setOf()

        val result: MutableList<List<Int>> = mutableListOf()

        val columnIndexInBoard = subBoardIndex % 3
        val selectedSubBoardIndex = (board.indices).map { it }.filter {
            it % 3 == columnIndexInBoard
        }


        val columnIndexInSubBoard = columnInSubBoard % 3

        for (i in selectedSubBoardIndex) {
            val row = board[i].filterIndexed { index, _ -> index % 3 == columnIndexInSubBoard }
            result.add(row)
        }
        return result.flatten().toSet()
    }

    fun getPossibleNumbersFromRowAndColumn(
        board: List<List<Int>>,
        subBoardIndex: Int,
        rowInSubBoard: Int,
        columnInSubBoard: Int
    ): Set<Int> {

//        println("$board, subBoardIndex: $subBoardIndex, row $rowInSubBoard, col $columnInSubBoard")
        val filledNumInRow = numInSameRow(board, subBoardIndex, rowInSubBoard)
//        println(filledNumInRow)
        val filledNumInCol = numsInSameColumn(board, subBoardIndex, columnInSubBoard)
        val filledNum = filledNumInRow.union(filledNumInCol)
        val numbers = (1..9).map { it }.toSet()

        return numbers.subtract(filledNum)
    }

    fun generateBoard(
        currentBoardIndex: Int = 0,
        board: MutableList<List<Int>> = mutableListOf()
    ): List<List<Int>> {
        if (currentBoardIndex == 9) return board
        val subBoard =
            this.filledSubBoard(parentBoard = board, currentSubBoardIndex = currentBoardIndex)
        board.add(subBoard)
        println(board)
        return generateBoard(currentBoardIndex + 1, board)
    }
}

class Sodoku2(override val GRID_SIZE: Int) : ISodoku(){
    override fun isInRow(board: List<List<Int>>, row: Int, number: Int): Boolean {
        for(i in 0 until GRID_SIZE){
            if(board[row][i] == number) return true
        }
        return false
    }
    override fun isInColumn(board: List<List<Int>>, column: Int, number: Int): Boolean {
        for (i in 0 until GRID_SIZE){
            if(board[i][column] == number) return true
        }
        return false
    }
    override fun isInBoard(board: List<List<Int>>, row: Int, column: Int, number: Int): Boolean {
        val localBoxRow = row - row % 3
        val localBoxColumn = column - column % 3

        for (i in localBoxRow until localBoxRow + 3){
            for (j in localBoxColumn until localBoxColumn + 3){
                if(board[i][j] == number) return true
            }
        }

        return false
    }

    override fun isValidPlacement(
        board: List<List<Int>>,
        row: Int,
        column: Int,
        number: Int
    ): Boolean  = !isInColumn(board, column, number) && !isInRow(board, row, number) && !isInBoard(board, row, column, number)

    override fun solveBoard(board: MutableList<MutableList<Int>>): Boolean {
        for (row in 0 until GRID_SIZE){
            for (col in 0 until GRID_SIZE){
                if(board[row][col] != 0) continue
                for (number in 1..GRID_SIZE){
                    if(isValidPlacement(board, row, col, number)){
                        board[row][col] = number

                        when(solveBoard(board)){
                            true -> return true
                            else -> board[row][col] = 0
                        }
                    }
                }
                return false
            }
        }
        printBoard(board)
        return true
    }

    override fun emptiedBoard(board: MutableList<MutableList<Int>>, level: Int) : List<List<Int>> {
        if(level > GRID_SIZE || level < 1) throw Exception("level must be between 1 to $GRID_SIZE")

        for (row in 0 until GRID_SIZE){
            val elements =  (0 until GRID_SIZE).toMutableList()

            while (true){
                if(board[row].filter { it == 0 }.size == level) break

                val random = elements.random()
                if(board[row][random] != 0){
                    board[row][random] = 0
                }else{
                    elements.remove(random)
                }
            }
        }
        return board
    }

    override fun generateRandomSeed(seed: Int?): List<List<Int>> {
//        val seed = if(seed == null) (0 until GRID_SIZE).random() else seed
        val blankBoard = (0 until GRID_SIZE).map {
            (0 until GRID_SIZE).mapIndexed { index, i -> 0 }.toMutableList()
        }.toMutableList()

        blankBoard.mapIndexed {rowIndex, item ->
            val elements = (0 until GRID_SIZE).toMutableList()
            item.mapIndexed { colIndex, i ->
                if(colIndex == 0){// TODO: fix this seed problem 
                    while (true){
                        val random = elements.random()
                        if(isValidPlacement(blankBoard, rowIndex, colIndex, random)){
                            blankBoard[rowIndex][colIndex] = random
                            return blankBoard
                        }
                        elements.remove(random)
                    }
                }
            }
        }
        return blankBoard
    }

    override fun printBoard(board: List<List<Int>>) {
        board.forEachIndexed{rowIndex, row ->
            if(rowIndex % 3 == 0 && rowIndex != 0) print("\n-----------------------------")
            row.forEachIndexed { colIndex, col ->
                if(colIndex == 0) print("\n")
                else if(colIndex % 3 == 0) print(" | ")
                print("[${if(col == 0) " " else col}]")
            }
        }
    }
}