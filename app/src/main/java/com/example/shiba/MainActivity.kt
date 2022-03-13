package com.example.shiba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shiba.ui.theme.Glass
import com.example.shiba.ui.theme.ShibaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShibaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen() }
    }
}

@Composable
fun MainScreen() {
    val progresses = List(7) { it % 2 == 0 }.toMutableStateList()
    val onPanelClick: (Int) -> Unit = {
        progresses[it] = !progresses[it]
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "recent 7 days",
            fontSize = 20.sp
        )
        PanelRow(panels = progresses, onPanelClick = onPanelClick)
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