package com.septalfauzan.sudoku.core.domain

data class SudokuBoxCell(
    val value: Int,
    val isValid: Boolean? = null
)
