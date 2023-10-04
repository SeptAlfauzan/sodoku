package com.septalfauzan.sodoku.ui.features.home

import android.util.Log
import android.util.SparseArray
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.septalfauzan.sodoku.ui.components.InputButton
import com.septalfauzan.sodoku.ui.components.InputButtonType
import com.septalfauzan.sodoku.ui.components.NumberBoxItem
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(
    viewModel: HomeVewModel,
    boardState: List<List<Int>>,
    selectedRow: StateFlow<Int?>,
    selectedCol: StateFlow<Int?>,
    setSelectedCell: (row: Int?, col: Int?) -> Unit,
    updateBoard: (number: Int) -> Unit,
    modifier: Modifier = Modifier
) {


    Box(modifier = modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
//            LazyColumn {
//                items(9){
//                    NumberBoxItem(
//                        size = 32.dp,
//                        isSelected = it == selectedCol.collectAsState(
//                            initial = null
//                        ).value,
//                        selected = {
//                            setSelectedCell(0, it)
//                        },
//                        number = if (boardState[it] == 0) null else boardState[it],
//                        modifier = Modifier.padding(
//                            end = if ((it + 1) % 3 == 0) 12.dp else 4.dp,
//                            bottom = if ((it + 1) % 3 == 0) 12.dp else 4.dp
//                        ),
//                    )
//                }
//            }

//            Log.d("TAG", "HomeScreen: ${boardState[0].get(0)}")
            LazyColumn {
                items(9) { row ->
                    LazyRow {
                        items(9) { col ->
                            NumberBoxItem(
                                size = 32.dp,
                                isSelected = row == selectedRow.collectAsState(initial = null).value && col == selectedCol.collectAsState(
                                    initial = null
                                ).value,
                                selected = {
                                    setSelectedCell(row, col)
                                },
                                number = if (boardState[row][col] == 0) null else boardState[row][col],
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
                        updateBoard(0)
                        viewModel.updateNumber(0)
                    }) else InputButton(number = it + 1, onClick = {
                        updateBoard(it + 1)
//                        viewModel.updateNumber(it+1)
                    })
                }
            }
        }
    }
}