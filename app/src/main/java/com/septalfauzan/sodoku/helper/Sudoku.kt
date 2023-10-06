package com.septalfauzan.sodoku.helper

class Sudoku(override val gridSize: Int) : SudokuInterface() {

    /**
     * Method to check if number is already in board row or not
     * @param board two dimensional list sudoku board
     * @param row current row of board cell
     * @param number number that will be checked it already in board row or not
     * @return boolean status of is in board row or not
     */
    override fun isInRow(board: List<List<Int>>, row: Int, number: Int): Boolean {
        for (i in 0 until gridSize) {
            if (board[row][i] == number) return true
        }
        return false
    }


    /**
     * Method to check if number is already in board column or not
     * @param board two dimensional list sudoku board
     * @param column current column of board cell
     * @param number number that will be checked it already in board column or not
     * @return boolean status of is in board column or not
     */
    override fun isInColumn(board: List<List<Int>>, column: Int, number: Int): Boolean {
        for (i in 0 until gridSize) {
            if (board[i][column] == number) return true
        }
        return false
    }


    /**
     * Method to check if number is already in sub board (3*3 cells) or not
     * @param board two dimensional list sudoku board
     * @param row current row of board cell
     * @param column current column of board cell
     * @param number number that will be checked it already in board or not
     * @return boolean status of is in board or not
     */
    override fun isInBoard(board: List<List<Int>>, row: Int, column: Int, number: Int): Boolean {
        val localBoxRow = row - row % 3
        val localBoxColumn = column - column % 3

        for (i in localBoxRow until localBoxRow + 3) {
            for (j in localBoxColumn until localBoxColumn + 3) {
                if (board[i][j] == number) return true
            }
        }

        return false
    }

    /**
     * Method to determined is current placement of number in specific board cell is valid or not
     * @param board two dimensional list sudoku board
     * @param row current row of board cell
     * @param column current column of board cell
     * @param number number that will be assign in that board cell
     * @return boolean status of is valid placement or not
     */
    override fun isValidPlacement(
        board: List<List<Int>>,
        row: Int,
        column: Int,
        number: Int
    ): Boolean = !isInColumn(board, column, number) && !isInRow(board, row, number) && !isInBoard(
        board,
        row,
        column,
        number
    )

    /**
     * Method to solve given sudoku board
     * @param board two dimensional list sudoku board that will be solved
     * @return boolean of status board is solvable or not
     */
    override fun solveBoard(board: MutableList<MutableList<Int>>): Boolean {
        for (row in 0 until gridSize) {
            for (col in 0 until gridSize) {
                if (board[row][col] != 0) continue
                for (number in 1..gridSize) {
                    if (isValidPlacement(board, row, col, number)) {
                        board[row][col] = number

                        when (solveBoard(board)) {
                            true -> return true
                            else -> board[row][col] = 0
                        }
                    }
                }
                return false
            }
        }
        return true
    }

    /**
     * Method to clear few cells in board
     * @param board two dimensional list sudoku board that will be emptied
     * @param level level to determined how much cell will be cleared, higher more cell will be cleared
     * @return two dimensional list act as sudoku board
     */
    override fun emptiedBoard(board: MutableList<MutableList<Int>>, level: Int): List<List<Int>> {
        if (level > gridSize || level < 1) throw Exception("level must be between 1 to $gridSize")

        for (row in 0 until gridSize) {
            val elements = (0 until gridSize).toMutableList()

            while (true) {
                if (board[row].filter { it == 0 }.size == level) break

                val random = elements.random()
                if (board[row][random] != 0) {
                    board[row][random] = 0
                } else {
                    elements.remove(random)
                }
            }
        }
        return board
    }

    /**
     * Method to generate sudoku board with occupy cell
     * @param cellsOccupy used to defined how many cell need to occupy from blank board
     * @return two dimensional list act as sudoku board, with few cell occupy with valid number
     */
    override fun generateRandomSeed(cellsOccupy: Int): List<List<Int>> {
        if (cellsOccupy >= gridSize) throw Exception("Maximum cell occupied must be less than $gridSize!")

        try {
            val blankBoard = (0 until gridSize).map {
                (0 until gridSize).map { 0 }.toMutableList()
            }.toMutableList()
            val occupied = mutableListOf<Int>()
            val elements = (0 until gridSize).toMutableList()

            while (true) {
                val randomCol = (0 until gridSize).map { it }.random()
                val randomRow = (0 until gridSize).map { it }.random()
                val randomNumber = elements.random()

                if (occupied.size == cellsOccupy) return blankBoard

                if (isValidPlacement(blankBoard, randomRow, randomCol, randomNumber)) {
                    blankBoard[randomRow][randomCol] = randomNumber
                    occupied.add(randomNumber)
                    elements.remove(randomNumber)
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }


    /**
     * Method to print two dimensional list (board) in formatted way
     * @param board two dimensional list sudoku board
     */
    override fun printBoard(board: List<List<Int>>) {
        board.forEachIndexed { rowIndex, row ->
            if (rowIndex % 3 == 0 && rowIndex != 0) print("\n-----------------------------")
            row.forEachIndexed { colIndex, col ->
                if (colIndex == 0) print("\n")
                else if (colIndex % 3 == 0) print(" | ")
                print("[${if (col == 0) " " else col}]")
            }
        }
    }
}