package com.example.shiba

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun RegisterContent(handleAddClick: (String) -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var text by remember {
            mutableStateOf("")
        }
        Row(verticalAlignment = Alignment.Bottom) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(text = "name") },
                placeholder = { Text(text = "Enter name to register") }
            )
            Button(
                onClick = {
                    if (text.isNotEmpty()) {
                        handleAddClick(text)
                        Toast.makeText(context, "`$text` Registered", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .height(56.dp) // equals TextField height
                    .clip(shape = CircleShape)
            ) {
                Text(text = "Add")
            }
        }
    }
}