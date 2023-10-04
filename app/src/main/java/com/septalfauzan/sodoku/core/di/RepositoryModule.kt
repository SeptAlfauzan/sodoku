package com.septalfauzan.sodoku.core.di

import com.septalfauzan.sodoku.core.data.SudokuRepository
import com.septalfauzan.sodoku.core.domain.SudokuRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun provideSudokuRepository(sudokuRepository: SudokuRepository): SudokuRepositoryInterface
}