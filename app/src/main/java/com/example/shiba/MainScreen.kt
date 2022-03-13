package com.example.shiba

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalDate

@Composable
fun MainScreen(viewModel: CommitsViewModel = viewModel()) {
    val progressesDummy = List(7) { false }.toMutableStateList()
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

    val totalRecentProgress =
        viewModel.commitsInDatabase.observeAsState(initial = listOf()).toRecentProgress()
    val recentProgressMap: Map<String, SnapshotStateList<Int>> =
        uniqueCommitNames.associateWith {
            viewModel.commitsInDatabase.observeAsState(listOf()).toRecentProgressAbout(it)
        }
    val handleCommitClick: (String) -> Unit = {
        viewModel.insert(Commit(id = 0, it, LocalDate.now().toString()))
    }
    Scaffold(
        topBar =
        { TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }) },
        content = {
            DebugSection(
                allCommits = allCommitsInDatabase.value,
                daysWithCommits = daysWithCommits,
                onAddClick = { viewModel.insert(Commit(0, "a", LocalDate.now().toString())) },
                onClearClick = { viewModel.clear() }
            )
            when (tabItems[selectedTabIndex]) {
                TabItem.Lists -> ListContent(recentProgressMap, totalRecentProgress, onPanelClick)
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
fun DebugSection(
    allCommits: List<Commit>,
    daysWithCommits: List<String>,
    onAddClick: () -> Unit,
    onClearClick: () -> Unit
) {
    Column() {
        Text(text = allCommits.toString())
        Text(text = daysWithCommits.toString())
        Button(onClick = onAddClick) {
            Text(text = "add")
        }
        Button(onClick = onClearClick) {
            Text(text = "reset")
        }
    }

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