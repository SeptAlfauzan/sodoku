package com.septalfauzan.sodoku.core.domain.usecase

import androidx.compose.runtime.snapshots.SnapshotStateList

interface SudokuGameUseCaseInterface {
    fun getBoard(): List<List<Int>>
}