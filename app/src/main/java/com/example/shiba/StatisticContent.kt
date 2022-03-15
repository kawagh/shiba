package com.example.shiba

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun StatisticContent(allCommitsCount: Int) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Total Commits: $allCommitsCount",
            fontSize = 45.sp
        )
    }
}

@Preview
@Composable
fun PreviewStatisticContent() {
    StatisticContent(allCommitsCount = 4)
}
