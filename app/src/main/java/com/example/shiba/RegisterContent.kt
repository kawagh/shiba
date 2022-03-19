package com.example.shiba

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun RegisterContent(handleAddClick: (String) -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "register content")
        var text by remember {
            mutableStateOf("")
        }
        Row() {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(text = "name") },
            )
            Button(onClick = {
                if (text.isNotEmpty()) {
                    handleAddClick(text)
                    Toast.makeText(context, "`$text` Registered", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "Add")
            }
        }
    }
}