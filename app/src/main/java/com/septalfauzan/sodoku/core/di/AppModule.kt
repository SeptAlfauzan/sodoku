package com.septalfauzan.sodoku.core.di

import com.septalfauzan.sodoku.core.domain.usecase.SudokuGameUseCaseInterface
import com.septalfauzan.sodoku.core.domain.usecase.SudokuGameInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun provideSudokuGameUseCase(interactor: SudokuGameInteractor): SudokuGameUseCaseInterface
}