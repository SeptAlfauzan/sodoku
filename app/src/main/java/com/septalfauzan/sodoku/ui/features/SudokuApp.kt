package com.septalfauzan.sodoku.ui.features

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.septalfauzan.sodoku.helper.Screen
import com.septalfauzan.sodoku.ui.features.home.HomeScreen
import com.septalfauzan.sodoku.ui.features.home.HomeVewModel

@Composable
fun SudokuApp(homeVewModel: HomeVewModel) {
    Scaffold {
        Box(modifier = Modifier.padding(it)) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) {
                    HomeScreen(
                        viewModel = homeVewModel,
                        boardState = homeVewModel.boardState,
                        selectedCol = homeVewModel.selectedColumn,
                        selectedRow = homeVewModel.selectedRow,
                        setSelectedCell = {row, col -> homeVewModel.setSelectedCell(row, col)  },
                        updateBoard = { number ->
                            homeVewModel.updateBoard(
                                number,
                            )
                        })
                }
            }
        }
    }
}