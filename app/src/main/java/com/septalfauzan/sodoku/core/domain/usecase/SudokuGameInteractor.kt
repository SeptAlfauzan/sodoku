package com.septalfauzan.sodoku.core.domain.usecase

import android.content.Context
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.septalfauzan.sodoku.R
import com.septalfauzan.sodoku.core.domain.SudokuRepositoryInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SudokuGameInteractor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: SudokuRepositoryInterface
) : SudokuGameUseCaseInterface {
    override fun getBoard(): List<List<Int>> {
        val jsonStr = context.resources.openRawResource(R.raw.empty_board).bufferedReader()
            .use { it.readText() }
        return repository.getBoard(jsonStr)
    }
}