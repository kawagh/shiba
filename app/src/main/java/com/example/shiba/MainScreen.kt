package com.example.shiba

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shiba.network.UserInfoResponse
import com.example.shiba.ui.theme.HeavyGlass
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.time.LocalDate

@Composable
fun MainScreen(viewModel: CommitsViewModel = viewModel()) {
    val systemUiController = rememberSystemUiController()
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
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }
    val tabItems = listOf(TabItem.Lists, TabItem.Check, TabItem.Register, TabItem.Statistics)

    val allCommitsInDatabase: State<List<Commit>> =
        viewModel.commitsInDatabase.observeAsState(initial = listOf())
    // for StatContent
    val commitsCountMap: Map<String, Int> =
        allCommitsInDatabase.value.groupingBy { it.name }.eachCount()
    val gitHubUserInfo = viewModel.response.observeAsState(initial = UserInfoResponse(-1)).value


    val daysWithCommits = allCommitsInDatabase.value.map { it.date }.distinct()

    // for ListsContent
    val totalRecentProgress =
        viewModel.commitsInDatabase.observeAsState(initial = listOf()).toRecentProgress()
    val recentProgressMap: Map<String, SnapshotStateList<Int>> =
        uniqueCommitNames.associateWith {
            viewModel.commitsInDatabase.observeAsState(listOf()).toRecentProgressAbout(it)
        }
    val handleCommitClick: (String) -> Unit = {
        viewModel.insert(Commit(id = 0, it, LocalDate.now().toString()))
    }

    // for CheckContent
    val handleDeleteClick: (String) -> Unit = {
        viewModel.deleteCommitsAbout(it)
    }

    var isDeveloperMode by remember {
        mutableStateOf(false)
    }
    val onRegisterClick = { selectedTabIndex = tabItems.indexOf(TabItem.Register) }

    SideEffect {
        systemUiController.setSystemBarsColor(HeavyGlass)
    }


    Scaffold(
        topBar =
        {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = { isDeveloperMode = !isDeveloperMode }) {
                        Icon(Icons.Filled.Build, "toggle developer mode")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Filled.Delete, "delete commits")
                    }
                }
            )
        },
        content = {
            if (isDeveloperMode) {
                DebugSection(
                    allCommits = allCommitsInDatabase.value,
                    daysWithCommits = daysWithCommits,
                    onAddClick = { viewModel.insert(Commit(0, "a", LocalDate.now().toString())) },
                    onClearClick = { viewModel.clear() }
                )
            }
            if (showDeleteDialog) {
                DeleteDialog(
                    onConfirmClick = {
                        showDeleteDialog = false
                        viewModel.clear()
                    },
                    onDismissClick = {
                        showDeleteDialog = false
                    }
                )
            }
            when (tabItems[selectedTabIndex]) {
                TabItem.Lists -> ListContent(
                    recentProgressMap,
                    totalRecentProgress,
                    onPanelClick,
                    onRegisterClick,
                )
                TabItem.Check -> CheckContent(
                    commitNames = uniqueCommitNames,
                    onCommitClick = handleCommitClick,
                    onDeleteClick = handleDeleteClick,
                    onRegisterClick = onRegisterClick,
                )
                TabItem.Register -> RegisterContent(handleAddClick = handleCommitClick)
                TabItem.Statistics -> StatisticContent(
                    allCommitsInDatabase.value.size,
                    commitsCountMap,
                    gitHubUserInfo,
                )
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

@Composable
fun DeleteDialog(onConfirmClick: () -> Unit, onDismissClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "delete commits") },
        text = { Text(text = "If you delete, you never recovery your commits.") },
        confirmButton = {
            TextButton(onClick = onConfirmClick) {
                Text(text = "delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissClick) {
                Text(text = "cancel")
            }
        }
    )

}

sealed class TabItem(val name: String, val icon: ImageVector) {
    object Lists : TabItem("Lists", Icons.Filled.List)
    object Check : TabItem("Check", Icons.Filled.Done)
    object Register : TabItem("Register", Icons.Filled.Add)
    object Statistics : TabItem("Stat", Icons.Filled.Assessment)
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
