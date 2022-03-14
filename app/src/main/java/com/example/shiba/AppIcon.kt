package com.example.shiba

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shiba.ui.theme.Glass

@Preview
@Composable
fun AppIcon() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .offset(100.dp, 100.dp)
    ) {
        val width = size.minDimension / 2
        val height = size.minDimension / 2

        drawRect(
            color = Glass,
            size = Size(width, height)
        )
        drawRect(
            color = Color.White,
            size = Size(width / 2, height / 2)
        )
        drawLine(
            color = Color.White, start = Offset(0f, height / 2),
            end = Offset(width, height / 2),
            strokeWidth = 5f
        )
        drawLine(
            color = Color.White, start = Offset(width / 2, 0f),
            end = Offset(width / 2, height),
            strokeWidth = 5f
        )

    }
}
