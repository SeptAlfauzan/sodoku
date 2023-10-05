package com.septalfauzan.sodoku.core.domain.usecase

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.septalfauzan.sodoku.core.domain.SudokuRepositoryInterface
import javax.inject.Inject

class SudokuGameInteractor @Inject constructor(private val repository: SudokuRepositoryInterface) : SudokuGameUseCaseInterface {
    override fun getBoard(jsonString: String): List<List<Int>> = repository.getBoard(jsonString)
}