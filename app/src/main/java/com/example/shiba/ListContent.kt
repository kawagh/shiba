package com.example.shiba

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shiba.ui.theme.Glass

@Composable
fun ListContent(
    recentProgresses: Map<String, SnapshotStateList<Boolean>>,
    progressesTotal: SnapshotStateList<Boolean>,
    onPanelClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "recent 7 days",
            fontSize = 20.sp
        )
        recentProgresses.forEach { (commitName, progresses) ->
            CommitLines(name = commitName, progresses = progresses, onPanelClick = onPanelClick)
        }
        CommitLines(
            name = "total",
            progresses = progressesTotal,
            onPanelClick = onPanelClick
        )
    }

}

@Composable
fun CommitLines(
    name: String,
    progresses: SnapshotStateList<Boolean>,
    onPanelClick: (Int) -> Unit
) {
    Column {
        Text(text = name)
        PanelRow(progresses, onPanelClick = onPanelClick)
    }
}

@Composable
fun PanelRow(
    panels: SnapshotStateList<Boolean>,
    onPanelClick: (Int) -> Unit,
) {
    Row {
        panels.forEachIndexed { index, panel ->
            Panel(
                panel,
                onClick = { onPanelClick(index) }
            )
        }
    }
}

@Composable
fun Panel(panel: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(30.dp),
        colors = when (panel) {
            true ->
                ButtonDefaults.textButtonColors(
                    backgroundColor = Glass
                )
            false ->
                ButtonDefaults.textButtonColors(
                    backgroundColor = Color.White
                )
        }
    ) {
    }
}
