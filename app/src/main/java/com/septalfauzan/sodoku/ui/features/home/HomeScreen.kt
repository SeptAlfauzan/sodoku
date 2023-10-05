package com.septalfauzan.sodoku.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.septalfauzan.sodoku.ui.components.InputButton
import com.septalfauzan.sodoku.ui.components.InputButtonType
import com.septalfauzan.sodoku.ui.components.NumberBoxItem
import com.septalfauzan.sodoku.ui.theme.GrayBlue
import com.septalfauzan.sodoku.utils.LayoutType
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.floor

@Composable
fun HomeScreen(
    windowSize: WindowWidthSizeClass,
    viewModel: HomeVewModel,
    boardState: List<List<Int>>,
    selectedRow: StateFlow<Int?>,
    selectedCol: StateFlow<Int?>,
    setSelectedCell: (row: Int?, col: Int?) -> Unit,
    updateBoard: (number: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var layoutType: LayoutType by remember { mutableStateOf(LayoutType.PORTRAIT) }
    layoutType = when (windowSize) {
        WindowWidthSizeClass.Compact -> LayoutType.PORTRAIT
        WindowWidthSizeClass.Medium -> LayoutType.MEDIUM
        else -> LayoutType.LARGE
    }

    if (layoutType != LayoutType.LARGE) {
        LayoutPotrait(
            viewModel,
            boardState,
            selectedRow,
            selectedCol,
            setSelectedCell,
            updateBoard,
            modifier
        )
    } else {
        LayoutLandscape(
            viewModel,
            boardState,
            selectedRow,
            selectedCol,
            setSelectedCell,
            updateBoard,
            modifier
        )
    }

}

@Composable
fun LayoutPotrait(
    viewModel: HomeVewModel,
    boardState: List<List<Int>>,
    selectedRow: StateFlow<Int?>,
    selectedCol: StateFlow<Int?>,
    setSelectedCell: (row: Int?, col: Int?) -> Unit,
    updateBoard: (number: Int) -> Unit,
    modifier: Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.secondary)
                        .padding(horizontal = 24.dp, vertical = 4.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back button",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Box(
                    Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.secondary)
                        .padding(horizontal = 24.dp, vertical = 4.dp)
                ) {
                    Text("01:23", fontSize = 24.sp, color = MaterialTheme.colorScheme.primary)
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.secondary)
                        .padding(horizontal = 24.dp, vertical = 4.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "retry button",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Box(Modifier.height(44.dp))

            Column(Modifier.clip(RoundedCornerShape(4.dp)).background(MaterialTheme.colorScheme.inversePrimary).padding(8.dp)) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(9),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(81) {
                        val row = floor(it / 9.0).toInt()
                        val col = it % 9
                        NumberBoxItem(
                            isSelected = row == selectedRow.collectAsState(initial = null).value && col == selectedCol.collectAsState(
                                initial = null
                            ).value,
                            selected = { setSelectedCell(row, col) },
                            number = if (boardState[row][col] == 0) null else boardState[row][col],
                            modifier = Modifier.height(36.dp)
                        )
                    }
                }

                Box(modifier = Modifier.height(32.dp))
                LazyVerticalGrid(columns = GridCells.Fixed(5), verticalArrangement = Arrangement.spacedBy(4.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
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
}

@Composable
fun LayoutLandscape(
    viewModel: HomeVewModel,
    boardState: List<List<Int>>,
    selectedRow: StateFlow<Int?>,
    selectedCol: StateFlow<Int?>,
    setSelectedCell: (row: Int?, col: Int?) -> Unit,
    updateBoard: (number: Int) -> Unit,
    modifier: Modifier
) {

    Box(modifier = modifier.fillMaxSize()) {
        Row(
            Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                columns = GridCells.Fixed(9),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(81) {
                    val row = floor(it / 9.0).toInt()
                    val col = it % 9

                    Row(Modifier.size(32.dp)) {
                        NumberBoxItem(
                            isSelected = row == selectedRow.collectAsState(initial = null).value && col == selectedCol.collectAsState(
                                initial = null
                            ).value,
                            selected = { setSelectedCell(row, col) },
                            number = if (boardState[row][col] == 0) null else boardState[row][col],
                            modifier = Modifier.size(32.dp)
                        )
                        if (col % 3 == 2) Box(modifier = Modifier.size(10.dp))
                    }

                }
            }

            Box(modifier = Modifier.width(28.dp))
            LazyVerticalGrid(columns = GridCells.Fixed(5), modifier = Modifier.weight(1f)) {
                items(10) {
                    if (it == 9) InputButton(type = InputButtonType.ERASER, onClick = {
                        updateBoard(0)
                        viewModel.updateNumber(0)
                    }) else InputButton(number = it + 1, onClick = {
                        updateBoard(it + 1)
//                        viewModel.updateNumber(it+1)
                    }, modifier = Modifier.height(62.dp))
                }
            }
        }
    }
}
