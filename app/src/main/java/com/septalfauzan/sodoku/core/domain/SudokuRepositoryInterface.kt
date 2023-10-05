package com.septalfauzan.sodoku.core.domain

interface SudokuRepositoryInterface {
    fun getBoard(jsonStr: String): List<List<Int>>
}