package com.example.shiba.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp


@Composable
fun GuideToRegisterContent(onRegisterClick: () -> Unit) {
    Column() {
        Text(text = "There is no task.", fontSize = 25.sp)
        Button(onClick = onRegisterClick) {
            Text(text = "register new task")
        }
    }
}
