package com.septalfauzan.sudoku.ui.components

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.septalfauzan.sudoku.R
import com.septalfauzan.sudoku.ui.theme.SudokuTheme

@Composable
fun GameLoading(modifier: Modifier = Modifier){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_hand))

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
}

@Preview
@Composable
private fun Preview(){
    SudokuTheme {
        Surface{
            GameLoading()
        }
    }
}