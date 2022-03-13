package com.example.shiba

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CheckContent(commitNames: List<String>, onCommitClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        items(commitNames) {
            Row() {
                Text(text = "daily tasks: $it", fontSize = 25.sp)
                Button(onClick = { onCommitClick(it) }) {
                    Icon(Icons.Default.Done, "done")
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}