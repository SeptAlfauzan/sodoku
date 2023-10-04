package com.septalfauzan.sodoku.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.septalfauzan.sodoku.R

enum class InputButtonType {
    NUMBER,
    ERASER
}

@Composable
fun InputButton(
    type: InputButtonType = InputButtonType.NUMBER,
    onClick: () -> Unit,
    number: Int? = null,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .padding(4.dp)
        .size(48.dp)
        .clip(RoundedCornerShape(8.dp))
        .clickable { onClick() }
        .background(MaterialTheme.colors.secondary),
        contentAlignment = Alignment.Center
    ) {
        if (type == InputButtonType.NUMBER) {
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.eraser_1),
                contentDescription = "eraser"
            )
        }
    }
}