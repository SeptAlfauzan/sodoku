package com.septalfauzan.sudoku.ui.features

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.septalfauzan.sudoku.helper.Screen
import com.septalfauzan.sudoku.ui.features.home.HomeScreen
import com.septalfauzan.sudoku.ui.features.home.HomeVewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SudokuApp(
    homeVewModel: HomeVewModel, windowSize: WindowWidthSizeClass,
) {
    Scaffold {
        Box(modifier = Modifier.padding(it)) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) {
                    HomeScreen(
                        windowSize = windowSize,
                        boardState = homeVewModel.boardState,
                        selectedRow = homeVewModel.selectedRow,
                        selectedColumn = homeVewModel.selectedColumn,
                        loadingBoard = homeVewModel.loadingBoard,
                        updateBoard = {number -> homeVewModel.updateBoard(number)},
                        setSelectedCell = { row, col -> homeVewModel.setSelectedCell(row, col) },
                    )
                }
            }
        }
    }
}