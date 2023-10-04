package com.septalfauzan.sodoku.helper

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Game : Screen("game")
    object Score : Screen("score")
    object Setting : Screen("setting")
}