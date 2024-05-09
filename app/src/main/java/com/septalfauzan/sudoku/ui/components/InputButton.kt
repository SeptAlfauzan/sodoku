package com.septalfauzan.sudoku.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.septalfauzan.sudoku.R

enum class InputButtonType {
    NUMBER,
    ERASER,
    BACK,
    RETRY
}

@Composable
fun InputButton(
    type: InputButtonType = InputButtonType.NUMBER,
    onClick: () -> Unit,
    number: Int? = null,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .size(48.dp)
        .clip(RoundedCornerShape(8.dp))
        .clickable { onClick() }
        .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        when (type) {
            InputButtonType.NUMBER -> Text(
                text = number.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )

            InputButtonType.ERASER -> Image(
                painter = painterResource(id = R.drawable.eraser_1),
                contentDescription = "eraser"
            )

            InputButtonType.RETRY -> Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(R.string.retry_button),
                tint = MaterialTheme.colorScheme.primary
            )

            InputButtonType.BACK -> Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back_button),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}