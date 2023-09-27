package com.septalfauzan.sodoku.helper

class Sudoku(override val GRID_SIZE: Int) : ISodoku(){
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