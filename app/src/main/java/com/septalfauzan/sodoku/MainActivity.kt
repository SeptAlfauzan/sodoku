package com.septalfauzan.sodoku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.septalfauzan.sodoku.ui.features.SudokuApp
import com.septalfauzan.sodoku.ui.features.home.HomeVewModel
import com.septalfauzan.sodoku.ui.theme.SodokuTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SodokuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val homeVewModel: HomeVewModel by viewModels()
                    SudokuApp(homeVewModel = homeVewModel)
                }
            }
        }
    }
}