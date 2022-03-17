package com.example.shiba

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shiba.ui.theme.Glass
import com.example.shiba.ui.theme.HeavyGlass
import com.example.shiba.ui.theme.LightGlass
import com.example.shiba.ui.theme.Purple200
import java.time.LocalDate

@Composable
fun ListContent(
    recentProgresses: Map<String, SnapshotStateList<Int>>,
    progressesTotal: SnapshotStateList<Int>,
    onPanelClick: (Int) -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "recent $DAYS_IN_A_WEEK days",
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
        if (recentProgresses.keys.isEmpty()) {
            Spacer(modifier = Modifier.size(25.dp))
            GuideToRegisterContent(onRegisterClick = onRegisterClick)
        }
    }
}

@Composable
fun CommitLines(
    name: String,
    progresses: SnapshotStateList<Int>,
    onPanelClick: (Int) -> Unit
) {
    Column {
        Text(text = name)
        PanelRow(progresses, onPanelClick = onPanelClick)
    }
}

@Composable
fun PanelRow(
    panels: SnapshotStateList<Int>,
    onPanelClick: (Int) -> Unit,
) {
    Row {
        panels.forEachIndexed { index, commitCount ->
            Panel(
                commitCount,
                onClick = { onPanelClick(index) }
            )
        }
    }
}

@Composable
fun Panel(commitCount: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(30.dp),
        colors = when (commitCount) {
            0 ->
                ButtonDefaults.textButtonColors(
                    backgroundColor = Color.White
                )
            1 ->
                ButtonDefaults.textButtonColors(
                    backgroundColor = LightGlass
                )
            in 2..3 ->
                ButtonDefaults.textButtonColors(
                    backgroundColor = Glass
                )
            else -> ButtonDefaults.textButtonColors(
                backgroundColor = HeavyGlass
            )
        }
    ) {
    }
}
