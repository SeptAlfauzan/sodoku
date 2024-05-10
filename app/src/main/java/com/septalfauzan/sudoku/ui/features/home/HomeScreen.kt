package com.septalfauzan.sudoku.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.septalfauzan.sudoku.R
import com.septalfauzan.sudoku.core.domain.SudokuBoxCell
import com.septalfauzan.sudoku.helper.DataMapper.toSudokuBoxCellStateList
import com.septalfauzan.sudoku.helper.formatTimer
import com.septalfauzan.sudoku.ui.common.GameState
import com.septalfauzan.sudoku.ui.widgets.InputButton
import com.septalfauzan.sudoku.ui.widgets.InputButtonType
import com.septalfauzan.sudoku.ui.widgets.NumberBoxItem
import com.septalfauzan.sudoku.ui.features.loading.LoadingScreen
import com.septalfauzan.sudoku.ui.theme.SudokuTheme
import com.septalfauzan.sudoku.ui.widgets.AlertDialogCustom
import com.septalfauzan.sudoku.utils.LayoutType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import java.lang.Exception
import kotlin.math.floor

@Composable
fun HomeScreen(
    windowSize: WindowWidthSizeClass,
    boardState: List<List<SudokuBoxCell>>,
    gameState: GameState,
    selectedRow: StateFlow<Int?>,
    selectedColumn: StateFlow<Int?>,
    loadingBoard: StateFlow<Boolean>,
    restartGame: () -> Unit,
    setSelectedCell: (row: Int?, col: Int?) -> Unit,
    updateBoard: (number: Int) -> Unit,
    gameLife: Int,
    initialGameLife: Int,
    countDownTimer: Int,
    modifier: Modifier = Modifier
) {
    var layoutType: LayoutType by remember { mutableStateOf(LayoutType.PORTRAIT) }
    layoutType = when (windowSize) {
        WindowWidthSizeClass.Compact -> LayoutType.PORTRAIT
        WindowWidthSizeClass.Medium -> LayoutType.MEDIUM
        else -> LayoutType.LARGE
    }

    loadingBoard.collectAsState(initial = true).value.let { loading ->
        when (loading) {
            true -> LoadingScreen()
            false -> {
                if(gameState == GameState.GAME_OVER) AlertDialogCustom(
                    onDismissRequest = { /*TODO*/ },
                    onConfirmation = restartGame,
                    dialogTitle = "Game Over!",
                    dialogText = "lorem ipsum dolor emet",
                    icon = Icons.Default.Warning
                )
                if (layoutType != LayoutType.LARGE) {
                    LayoutPotrait(
                        boardState,
                        selectedRow,
                        selectedColumn,
                        setSelectedCell,
                        updateBoard,
                        gameLife,
                        restartGame,
                        initialGameLife,
                        countDownTimer,
                        modifier
                    )
                } else {
                    LayoutLandscape(
                        boardState,
                        selectedRow,
                        selectedColumn,
                        setSelectedCell,
                        updateBoard,
                        gameLife,
                        restartGame,
                        initialGameLife,
                        countDownTimer,
                        modifier
                    )
                }
            }
        }
    }
}

@Composable
fun LayoutPotrait(
    boardState: List<List<SudokuBoxCell>>,
    selectedRow: StateFlow<Int?>,
    selectedCol: StateFlow<Int?>,
    setSelectedCell: (row: Int?, col: Int?) -> Unit,
    updateBoard: (number: Int) -> Unit,
    gameLife: Int,
    restartGame: () -> Unit,
    initialGameLife: Int,
    countDownTimer: Int,
    modifier: Modifier
) {

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InputButton(onClick = {
                    try {// TODO: DELETE THIS !!
                        throw RuntimeException("test error")
                    } catch (e: Exception) {
                        Timber.e(e)
                    }
                }, type = InputButtonType.BACK)
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .height(48.dp)
                        .padding(horizontal = 24.dp, vertical = 4.dp)
                ) {
                    Text(
                        formatTimer(countDownTimer),
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                InputButton(onClick = restartGame, type = InputButtonType.RETRY)
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(initialGameLife) {
                    Icon(
                        imageVector =
                        if (it < gameLife)
                            Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(R.string.lifepoint_ic),
                        tint = if (it < gameLife) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
            Column(
                Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.inversePrimary)
                    .padding(16.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(9),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(81) {
                        val row = floor(it / 9.0).toInt()
                        val col = it % 9
                        val cell: SudokuBoxCell = boardState[row][col]
                        NumberBoxItem(
                            isSelected = row == selectedRow.collectAsState(initial = null).value && col == selectedCol.collectAsState(
                                initial = null
                            ).value,
                            isValid = cell.isValid ?: true,
                            selected = { setSelectedCell(row, col) },
                            number = if (cell.value == 0) null else cell.value,
                            modifier = Modifier.height(36.dp)
                        )
                    }
                }

                Box(modifier = Modifier.height(32.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(10) {
                        if (it == 9) InputButton(type = InputButtonType.ERASER, onClick = {
                            updateBoard(0)
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
    boardState: List<List<SudokuBoxCell>>,
    selectedRow: StateFlow<Int?>,
    selectedCol: StateFlow<Int?>,
    setSelectedCell: (row: Int?, col: Int?) -> Unit,
    updateBoard: (number: Int) -> Unit,
    gameLife: Int,
    restartGame: () -> Unit,
    initialGameLife: Int,
    countDownTimer: Int,
    modifier: Modifier
) {

    Box(modifier = modifier.fillMaxSize()) {
        Row(
            Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    columns = GridCells.Fixed(9),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(81) {
                        val row = floor(it / 9.0).toInt()
                        val col = it % 9

                        val cell: SudokuBoxCell = boardState[row][col]

                        NumberBoxItem(
                            isSelected = row == selectedRow.collectAsState(initial = null).value && col == selectedCol.collectAsState(
                                initial = null
                            ).value,
                            isValid = cell.isValid ?: true,
                            selected = { setSelectedCell(row, col) },
                            number = if (cell.value == 0) null else cell.value,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

            Box(modifier = Modifier.width(28.dp))
            Column(Modifier.weight(1f)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    InputButton(onClick = { /*TODO*/ }, type = InputButtonType.BACK)
                    Box(
                        Modifier
                            .height(48.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .weight(1f)
                            .padding(horizontal = 24.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Time 01:23",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    InputButton(onClick = { /*TODO*/ }, type = InputButtonType.RETRY)
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 32.dp)
                ) {
                    items(10) {
                        if (it == 9) InputButton(type = InputButtonType.ERASER, onClick = {
                            updateBoard(0)
                        }) else InputButton(number = it + 1, onClick = {
                            updateBoard(it + 1)
                        }, modifier = Modifier.height(48.dp))
                    }
                }
            }
        }
    }
}


@Preview(device = Devices.PIXEL_4, showBackground = true)
@Composable
private fun Preview() {
    val blankDummyBoard = listOf(
        listOf(0, 0, 0, 3, 0, 4, 0, 9, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
    )

    SudokuTheme {
        Surface {
            HomeScreen(
                windowSize = WindowWidthSizeClass.Compact,
                boardState = blankDummyBoard.toSudokuBoxCellStateList(),
                selectedRow = MutableStateFlow(0),
                selectedColumn = MutableStateFlow(0),
                loadingBoard = MutableStateFlow(false),
                setSelectedCell = { _, _ -> },
                gameLife = 2,
                initialGameLife = 2,
                countDownTimer = 120,
                gameState = GameState.NOT_STARTED,
                restartGame = {},
                updateBoard = { })
        }
    }
}