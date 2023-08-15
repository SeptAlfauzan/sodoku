package com.septalfauzan.sodoku.helper

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.DecimalFormat
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log

internal class SodokuTest {
    @Test
    fun generator_board_value() {
        val availableNums = (1..9).map { it }
        val result = Sodoku.randomGenerator(listAvailableNum = listOf(1))
        println(result)
    }

    @Test
    fun check_input_is_valid_on_board() {
        val board = listOf<Int>(1, 5, 2)
        val result = Sodoku.checkIsValid(9, board)
        assertEquals(result, true)
    }

    @Test
    fun generate_available_num() {
        val filledBoard = listOf<Int>(1, 5, 2)
        val result = Sodoku.generateAvailableBoard(filledBoard)
        assertEquals(result, listOf(3, 4, 6, 7, 8, 9))
    }

    @Test
    fun generate_sub_board() {
        val result = Sodoku.filledSubBoard()
        println("result board $result")
        assertEquals(result.size, 9)
    }

    @Test
    fun check_num_in_row_is_valid() {
//        val board: MutableList<List<Int>> = mutableListOf()
//        for (i in 0 until 9){
//            board.add(Sodoku.filledSubBoard())
//        }
//
//        var currentRow = 0f
//        board.mapIndexed { index, ints ->
//            val row = floor(index/3f)
//            if(row != currentRow){
//                currentRow = row
//                println()
//            }
//            print(ints)
//        }
        val board = listOf(
            listOf(5, 4, 8, 6, 7, 3, 1, 2, 9),
            listOf(1, 2, 4, 6, 8, 7, 5, 9, 3),
            listOf(4, 2, 8, 7, 9, 5, 1, 3, 6),
            listOf(2, 8, 9, 7, 6, 1, 5, 4, 3),
            listOf(3, 5, 7, 6, 1, 9, 4, 2, 8),
            listOf(9, 5, 7, 2, 6, 3, 8, 4, 1),
            listOf(2, 8, 4, 5, 9, 1, 3, 7, 6),
            listOf(6, 9, 3, 1, 7, 2, 5, 4, 8),
            listOf(9, 2, 3, 4, 5, 6, 8, 1, 7),
        )
//        5, 4, 8,    1, 2, 4,    4, 2, 8,
//        6, 7, 3,    6, 8, 7     7, 9, 5,
//        1, 2, 9     5, 9, 3     1, 3, 6
//
//        2, 8, 9,    3, 5, 7,    9, 5, 7
//        7, 6, 1,    6, 1, 9,    2, 6, 3,
//        5, 4, 3     4, 2, 8     8, 4, 1
//
//        2, 8, 4,    6, 9, 3     9, 2, 3,
//        5, 9, 1     1, 7, 2,    4, 5, 6,
//        3, 7, 6     5, 4, 8     8, 1, 7

        val result = Sodoku.checkRowContainSameNum(board, 1, 0, 1)
        assertEquals(result, true)
    }
    @Test
    fun check_num_in_col_is_valid(){
        val board = listOf(
            listOf(5, 4, 8, 6, 7, 3, 1, 2, 9),
            listOf(1, 2, 4, 6, 8, 7, 5, 9, 3),
            listOf(4, 2, 8, 7, 9, 5, 1, 3, 6),
            listOf(2, 8, 9, 7, 6, 1, 5, 4, 3),
            listOf(3, 5, 7, 6, 1, 9, 4, 2, 8),
            listOf(9, 5, 7, 2, 6, 3, 8, 4, 1),
            listOf(2, 8, 4, 5, 9, 1, 3, 7, 6),
            listOf(6, 9, 3, 1, 7, 2, 5, 4, 8),
            listOf(9, 2, 3, 4, 5, 6, 8, 1, 7),
        )
//        5, 4, 8,    1, 2, 4,    4, 2, 8,
//        6, 7, 3,    6, 8, 7     7, 9, 5,
//        1, 2, 9     5, 9, 3     1, 3, 6
//
//        2, 8, 9,    3, 5, 7,    9, 5, 7
//        7, 6, 1,    6, 1, 9,    2, 6, 3,
//        5, 4, 3     4, 2, 8     8, 4, 1
//
//        2, 8, 4,    6, 9, 3     9, 2, 3,
//        5, 9, 1     1, 7, 2,    4, 5, 6,
//        3, 7, 6     5, 4, 8     8, 1, 7

        println("asdasdsadasd asdkljasdlkj")
        val result = Sodoku.checkColumnContainSameNum(board, 1, 0, 1)
        assertEquals(result, true)
    }
}