package com.septalfauzan.sodoku.core.domain

interface SudokuRepositoryInterface {
    fun getBoard(): List<List<Int>>
}