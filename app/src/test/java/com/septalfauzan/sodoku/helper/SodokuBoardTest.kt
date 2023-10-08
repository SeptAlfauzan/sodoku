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
    private lateinit var sudoku: Sudoku

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
        sudoku = Sudoku(9)
    }

    @Test
    fun isInRow() {
        val result = sudoku.isInRow(dummyBoard, 0, 6)
        assertEquals(true, result)
    }

    @Test
    fun isInColumn() {
        val result = sudoku.isInColumn(dummyBoard, 2, 3)
        assertEquals(true, result)
    }

    @Test
    fun isInBoard() {
        val result = sudoku.isInBoard(dummyBoard, 4, 2, 3)
        val expected = true
        assertEquals(expected, result)
    }

    @Test
    fun isValidPlacement() {
        val result = sudoku.isValidPlacement(dummyBoard, 4, 3, 1)
        val expected = true
        assertEquals(expected, result)
    }

    @Test
    fun generateBoard() {
        val result = sudoku.solveBoard(dummyBoard.map { it.toMutableList() }.toMutableList())

        val expected = true
        assertEquals(expected, result)
    }


    @Test
    fun generateUnsolvedBoard() {
        val board = dummyBoard.toDeepMutableList()
//        val result = sodoku.solveBoard(board)

        val unsolved = sudoku.emptiedBoard(board, 8)
        sudoku.printBoard(unsolved)
    }

    @Test
    fun generateUnsolvedBoardFromBlankBoard() {
        val board = blankDummyBoard.map { it.toMutableList() }.toMutableList()
        sudoku.solveBoard(board)

        println("\nsolved board")
        sudoku.printBoard(board)

        val unsolved = sudoku.emptiedBoard(board, 8)
        println("\nresult (emptied board)")
        sudoku.printBoard(unsolved)
    }

    @Test
    fun generateBoardWithSeed(){
        val board = sudoku.generateRandomSeed(8)
        sudoku.printBoard(board)

        assertNotNull(board)
    }

    @Test
    fun fail_generate_board_with_seed(){
        val exception = assertThrows(Exception::class.java){
            sudoku.generateRandomSeed(9)
        }

        assertEquals(exception.message, "Maximum cell occupied must be less than ${sudoku.gridSize}!")
    }

    @Test
    fun generate_20_solvable_board_from_random_seed_board(){
        repeat(20){
            val board = sudoku.generateRandomSeed(8)
            val solvable = sudoku.solveBoard(board.toDeepMutableList())
            assertEquals(true, solvable)
        }
    }

    @Test
    fun compare_two_cell_value_of_two_boards(){
        val fakeBoard = blankDummyBoard
        val fakeSolutionBoard = dummyBoard

        val result = sudoku.compareCellValueIgnoreZero(fakeBoard, fakeSolutionBoard, 0, 0)
        assertEquals(true, result)
    }

    @Test
    fun throw_exception_compare_two_cell_boards(){
        val fakeBoard = listOf<List<Int>>()
        val fakeSolutionBoard = dummyBoard

        val exception =  assertThrows(Exception::class.java){
            sudoku.compareCellValueIgnoreZero(fakeBoard, fakeSolutionBoard, 0, 0)
        }
        assertEquals("Both board and solution board size must be same", exception.message)
    }
}
