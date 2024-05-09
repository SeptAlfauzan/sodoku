package com.septalfauzan.sudoku.core.domain.usecase

import android.content.Context
import android.util.Log
import com.septalfauzan.sudoku.R
import com.septalfauzan.sudoku.core.domain.SudokuRepositoryInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SudokuGameInteractor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: SudokuRepositoryInterface
) : SudokuGameUseCaseInterface {
    override suspend fun getBoard(): List<List<Int>> {
        val jsonStr = context.resources.openRawResource(R.raw.empty_board).bufferedReader()
            .use { it.readText() }
        val gameReadyBoard = repository.getGameReadyBoard()
        val plainBoard = repository.getPlainBoard(jsonStr)
        Log.d("TAG", "get game ready board: $gameReadyBoard")
        Log.d("TAG", "get plain board: $plainBoard")
        return gameReadyBoard
    }
    override suspend fun getBoardSolution(board: List<List<Int>>): List<List<Int>> = repository.getBoardGameSollution(board)
    override fun compareBoardCell(
        value: Int,
        solutionBoard: List<List<Int>>,
        row: Int,
        col: Int
    ): Boolean {
        try {
            return repository.compareBoardsCell(value, solutionBoard, row, col)
        }catch (e: Exception){
            throw e
        }
    }
}