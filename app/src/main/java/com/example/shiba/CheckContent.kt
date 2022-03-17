package com.example.shiba

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CheckContent(
    commitNames: List<String>,
    onCommitClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
    onRegisterClick: () -> Unit,
) {
    var shouldSHowDelete by remember {
        mutableStateOf(false)
    }
    when (commitNames.isEmpty()) {
        true -> Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            GuideToRegisterContent(onRegisterClick = onRegisterClick)
        }
        false ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                items(commitNames) { commitName ->
                    Row() {
                        Text(text = "daily tasks: $commitName", fontSize = 25.sp)
                        Button(onClick = { onCommitClick(commitName) }) {
                            Icon(Icons.Default.Done, "done")
                        }
                        when (shouldSHowDelete) {
                            true -> {
                                IconButton(onClick = { shouldSHowDelete = !shouldSHowDelete }) {
                                    Icon(Icons.Filled.KeyboardArrowLeft, "hide button")
                                }
                                IconButton(onClick = { onDeleteClick(commitName) }) {
                                    Icon(Icons.Filled.Delete, "delete")
                                }
                            }
                            false -> {
                                IconButton(onClick = { shouldSHowDelete = !shouldSHowDelete }) {
                                    Icon(Icons.Filled.KeyboardArrowRight, "open button")
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
    }
}