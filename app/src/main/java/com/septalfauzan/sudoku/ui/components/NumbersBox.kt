package com.septalfauzan.sudoku.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.septalfauzan.sudoku.ui.theme.SudokuTheme

@Composable
fun NumberBoxItem(
    isSelected: Boolean,
    selected: () -> Unit,
    number: Int?,
    modifier: Modifier = Modifier,
    isValid: Boolean = true,
) {
    Box(modifier = modifier
        .clip(RoundedCornerShape(4.dp))
        .background(
            if (!isValid) {
                MaterialTheme.colorScheme.onErrorContainer
            } else {
                if (number == 0 || number == null)
                    MaterialTheme.colorScheme.surface
                else
                    MaterialTheme.colorScheme.secondary
            }
        )
        .border(
            border = BorderStroke(
                width = if (isSelected) 4.dp else 0.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
            ), shape = RoundedCornerShape(4.dp)
        )
        .clickable {
            selected()
        }) {
        Text(
            text = number?.toString() ?: "", style = MaterialTheme.typography.titleMedium.copy(
                textAlign = TextAlign.Center,
                color = if (!isValid) Color.White else MaterialTheme.colorScheme.primary
            ), modifier = Modifier
                .align(Alignment.Center)
                .size(24.dp)
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    val dummyBoard = mutableListOf(
        mutableListOf(7, 0, 2, 0, 5, 0, 6, 0, 0),
        mutableListOf(0, 0, 0, 0, 0, 3, 0, 0, 0),
        mutableListOf(1, 0, 0, 0, 0, 9, 5, 0, 0),
        mutableListOf(8, 0, 0, 0, 0, 0, 0, 9, 0),
        mutableListOf(0, 4, 3, 0, 0, 0, 7, 5, 0),
        mutableListOf(0, 9, 0, 0, 0, 0, 0, 0, 8),
        mutableListOf(0, 0, 9, 7, 0, 0, 0, 0, 5),
        mutableListOf(0, 0, 0, 2, 0, 0, 0, 0, 0),
        mutableListOf(0, 0, 7, 0, 4, 0, 2, 0, 3),
    )


    fun updateNumber(newNum: Int, row: Int, col: Int, board: MutableList<MutableList<Int>>) {
        if (row == -1 || col == -1) return
        board[row][col] = newNum
        Log.d("TAG", "updateNumber: $newNum")
    }

    SudokuTheme {
        Surface {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                var selectedRow: Int? by remember { mutableStateOf(null) }
                var selectedColumn: Int? by remember { mutableStateOf(null) }

                LazyColumn {
                    items(9) { row ->
                        LazyRow {
                            items(9) { col ->
                                NumberBoxItem(
                                    isSelected = row == selectedRow && col == selectedColumn,
                                    selected = {
                                        selectedRow = row
                                        selectedColumn = col
                                    },
                                    number = if (dummyBoard[row][col] == 0) null else dummyBoard[row][col],
                                    modifier = Modifier.padding(
                                        end = if ((col + 1) % 3 == 0) 12.dp else 4.dp,
                                        bottom = if ((row + 1) % 3 == 0) 12.dp else 4.dp
                                    ),
                                )
                            }
                        }
                    }
                }
                Box(modifier = Modifier.height(32.dp))
                LazyVerticalGrid(columns = GridCells.Fixed(5)) {
                    items(10) {
                        if (it == 9) InputButton(type = InputButtonType.ERASER, onClick = {
                            updateNumber(
                                newNum = it + 1,
                                row = selectedRow ?: -1,
                                col = selectedColumn ?: -1,
                                board = dummyBoard
                            )
                        }) else InputButton(number = it + 1, onClick = {
                            updateNumber(
                                newNum = 0,
                                row = selectedRow ?: -1,
                                col = selectedColumn ?: -1,
                                board = dummyBoard
                            )
                        })
                    }
                }
            }
        }
    }
}