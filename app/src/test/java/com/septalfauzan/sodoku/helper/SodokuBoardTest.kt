package com.septalfauzan.sodoku.helper

import com.septalfauzan.sodoku.helper.DataMapper.toDeepMutableList
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
internal class SudokuTest {
    private lateinit var sodoku: Sudoku

    private val blankDummyBoard = listOf(
        listOf(0, 0, 0, 3, 0, 4, 0, 9, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
    )

    private val dummyBoard = listOf(
        listOf(7, 0, 2, 0, 5, 0, 6, 0, 0),
        listOf(0, 0, 0, 0, 0, 3, 0, 0, 0),
        listOf(1, 0, 0, 0, 0, 9, 5, 0, 0),
        listOf(8, 0, 0, 0, 0, 0, 0, 9, 0),
        listOf(0, 4, 3, 0, 0, 0, 7, 5, 0),
        listOf(0, 9, 0, 0, 0, 0, 0, 0, 8),
        listOf(0, 0, 9, 7, 0, 0, 0, 0, 5),
        listOf(0, 0, 0, 2, 0, 0, 0, 0, 0),
        listOf(0, 0, 7, 0, 4, 0, 2, 0, 3),
    )

    @Before
    fun setup() {
        sodoku = Sudoku(9)
    }

    @Test
    fun isInRow() {
        val result = sodoku.isInRow(dummyBoard, 0, 6)
        assertEquals(true, result)
    }

    @Test
    fun isInColumn() {
        val result = sodoku.isInColumn(dummyBoard, 2, 3)
        assertEquals(true, result)
    }

    @Test
    fun isInBoard() {
        val result = sodoku.isInBoard(dummyBoard, 4, 2, 3)
        val expected = true
        assertEquals(expected, result)
    }

    @Test
    fun isValidPlacement() {
        val result = sodoku.isValidPlacement(dummyBoard, 4, 3, 1)
        val expected = true
        assertEquals(expected, result)
    }

    @Test
    fun generateBoard() {
        val result = sodoku.solveBoard(dummyBoard.map { it.toMutableList() }.toMutableList())

        val expected = true
        assertEquals(expected, result)
    }


    @Test
    fun generateUnsolvedBoard() {
        val board = dummyBoard.toDeepMutableList()
//        val result = sodoku.solveBoard(board)

        val unsolved = sodoku.emptiedBoard(board, 8)
        sodoku.printBoard(unsolved)
    }

    @Test
    fun generateUnsolvedBoardFromBlankBoard() {
        val board = blankDummyBoard.map { it.toMutableList() }.toMutableList()
        sodoku.solveBoard(board)

        println("\nsolved board")
        sodoku.printBoard(board)

        val unsolved = sodoku.emptiedBoard(board, 8)
        println("\nresult (emptied board)")
        sodoku.printBoard(unsolved)
    }

    @Test
    fun generateBoardWithSeed(){
        val board = sodoku.generateRandomSeed(8)
        sodoku.printBoard(board)

        assertNotNull(board)
    }

    @Test
    fun fail_generate_board_with_seed(){
        val exception = assertThrows(Exception::class.java){
            sodoku.generateRandomSeed(9)
        }

        assertEquals(exception.message, "Maximum cell occupied must be less than ${sodoku.gridSize}!")
    }
}
