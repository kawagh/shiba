package com.example.shiba

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shiba.ui.theme.Glass
import java.time.LocalDate

@Composable
fun MainScreen(viewModel: CommitsViewModel = viewModel()) {
    val progressesDummy = List(7) { false }.toMutableStateList()
    val progressesTotal = viewModel.hasCommitsInWeek()
    val onPanelClick: (Int) -> Unit = {
        progressesDummy[it] = !progressesDummy[it]
    }
    val uniqueCommitNames =
        viewModel.commitsInDatabase.observeAsState(initial = listOf()).value.map { it.name }
            .distinct()
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    val tabItems = listOf(TabItem.Lists, TabItem.Check, TabItem.Register)

    val allCommitsInDatabase: State<List<Commit>> =
        viewModel.commitsInDatabase.observeAsState(initial = listOf())
    val daysWithCommits = allCommitsInDatabase.value.map { it.date }.distinct()
    val recentProgress = viewModel.commitsInDatabase.observeAsState(listOf()).toRecentProgress()
    val handleCommitClick: (String) -> Unit = {
        viewModel.insert(Commit(id = 0, it, LocalDate.now().toString()))
    }
    Scaffold(
        topBar =
        { TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }) },
        content = {
            Column() {
                Text(
                    text = allCommitsInDatabase.value.toString()
                )
                Text(
                    text = daysWithCommits.toString()
                )
                Text(text = viewModel.getCommitIds().toString())
                Button(onClick = { viewModel.insert(Commit(0, "a", LocalDate.now().toString())) }) {
                    Text(text = "add")
                }
                Button(onClick = { viewModel.clear() }) {
                    Text(text = "reset")
                }
            }
            when (tabItems[selectedTabIndex]) {
                TabItem.Lists ->
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "recent 7 days",
                            fontSize = 20.sp
                        )
                        uniqueCommitNames.forEach {
                            ListsContent(
                                progresses = viewModel.hasCommitsInWeekAbout(it),
                                onPanelClick = onPanelClick,
                                name = it
                            )
                        }
                        ListsContent(
                            progresses = progressesTotal,
                            onPanelClick = onPanelClick,
                            name = "total"
                        )
                        ListsContent(
                            progresses = recentProgress,
                            onPanelClick = onPanelClick,
                            name = "dummy2"
                        )
                        ListsContent(
                            progresses = progressesDummy,
                            onPanelClick = onPanelClick,
                            name = "dummy"
                        )
                    }
                TabItem.Check -> CheckContent(
                    commitNames = uniqueCommitNames,
                    onCommitClick = handleCommitClick,
                )
                TabItem.Register -> RegisterContent(handleAddClick = handleCommitClick)
            }
        },
        bottomBar = {
            BottomNavigation(
                selectedTabIndex = selectedTabIndex,
                onTabClick = { selectedTabIndex = it },
                tabItems = tabItems
            )
        }
    )
}

sealed class TabItem(val name: String, val icon: ImageVector) {
    object Lists : TabItem("Lists", Icons.Filled.List)
    object Check : TabItem("Check", Icons.Filled.Done)
    object Register : TabItem("Register", Icons.Filled.Add)
}

@Composable
fun BottomNavigation(tabItems: List<TabItem>, selectedTabIndex: Int, onTabClick: (Int) -> Unit) {
    BottomNavigation {
        tabItems.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.name) },
                selected = selectedTabIndex == index,
                onClick = { onTabClick(index) }
            )
        }
    }
}

@Composable
fun ListsContent(
    name: String,
    progresses: SnapshotStateList<Boolean>,
    onPanelClick: (Int) -> Unit
) {
    Column {
        Text(text = name)
        PanelRow(progresses, onPanelClick = onPanelClick)
    }
}

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


@Composable
fun RegisterContent(handleAddClick: (String) -> Unit) {
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
                }
            }) {
                Text(text = "Add")
            }
        }
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
