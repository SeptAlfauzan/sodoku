package com.septalfauzan.sodoku.core.domain

data class SudokuBoxCell(
    val value: Int,
    val isValid: Boolean? = null
)
