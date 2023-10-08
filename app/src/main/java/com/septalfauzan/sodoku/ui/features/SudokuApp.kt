package com.septalfauzan.sodoku.ui.features

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
import com.septalfauzan.sodoku.helper.Screen
import com.septalfauzan.sodoku.ui.features.home.HomeScreen
import com.septalfauzan.sodoku.ui.features.home.HomeVewModel

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
                        viewModel = homeVewModel,
                        setSelectedCell = { row, col -> homeVewModel.setSelectedCell(row, col) },
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