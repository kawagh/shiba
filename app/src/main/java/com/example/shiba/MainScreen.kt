package com.example.shiba

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shiba.ui.theme.Glass

@Composable
fun MainScreen() {
    val progresses = List(7) { it % 2 == 0 }.toMutableStateList()
    val onPanelClick: (Int) -> Unit = {
        progresses[it] = !progresses[it]
    }
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    Scaffold(
        topBar =
        { TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }) },
        content = {
            when (selectedTabIndex) {
                0 -> {
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
                1 -> CheckContent()
                2 -> RegisterContent()
                else -> {
                    Text(text = "not reach")
                }
            }
        },
        bottomBar = {
            BottomNavigation(
                selectedTabIndex = selectedTabIndex,
                onTabClick = { selectedTabIndex = it })
        }
    )
}

@Composable
fun BottomNavigation(selectedTabIndex: Int, onTabClick: (Int) -> Unit) {
    val items = listOf("list", "check", "register")
    BottomNavigation {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                label = { Text(item) },
                selected = selectedTabIndex == index,
                onClick = { onTabClick(index) }
            )
        }
    }
}

@Composable
fun CheckContent() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        items(5) {
            Row() {
                Text(text = "daily tasks: $it", fontSize = 25.sp)
                Button(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Done, "done")
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}

@Composable
fun RegisterContent() {
    Text(text = "register content")
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
