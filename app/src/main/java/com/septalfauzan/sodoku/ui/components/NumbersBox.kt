package com.septalfauzan.sodoku.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.septalfauzan.sodoku.ui.theme.SodokuTheme

@Composable
fun NumberBox(
    parentBoxIndex: Int,
    selectedParentBoxState: Int?,
    updateSelectedParentBox: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedBox: Int? by remember { mutableStateOf(null) }
    LazyVerticalGrid(
        modifier = modifier.size(76.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        items(9) {
            Box(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        if (selectedBox == it && parentBoxIndex == selectedParentBoxState) MaterialTheme.colors.primary.copy(
                            alpha = 0.3f
                        ) else MaterialTheme.colors.secondary
                    )
                    .clickable {
                        updateSelectedParentBox(parentBoxIndex)
                        selectedBox = it
                    }
            ) {
                Text(
                    text = "$it",
                    style = MaterialTheme.typography.subtitle2.copy(
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primary
                    ),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
private fun Preview() {
    SodokuTheme() {
        Surface {
            Column(Modifier.fillMaxSize()) {
                var selectedParentBox: Int? by remember { mutableStateOf(null) }

                LazyVerticalGrid(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colors.primaryVariant)
                        .padding(12.dp)
                        .size(244.dp),
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(9) {index ->
                        NumberBox(parentBoxIndex = index,  selectedParentBoxState = selectedParentBox, updateSelectedParentBox = {selectedParentBox = it})
                    }
                }
            }
        }
    }
}