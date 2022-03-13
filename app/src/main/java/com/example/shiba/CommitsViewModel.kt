package com.example.shiba

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import java.sql.Date
import java.time.LocalDate

class CommitsViewModel : ViewModel() {

    private val dummyRecentCommits: List<Commit> = listOf(
        Commit(0, "dev", Date.valueOf(LocalDate.now().toString()).toString()),
        Commit(
            0, "dev", Date.valueOf(LocalDate.now().minusDays(1).toString()).toString()
        ),
        Commit(
            0, "read", Date.valueOf(LocalDate.now().minusDays(5).toString()).toString()
        ),
        Commit(
            0, "dev", Date.valueOf(LocalDate.now().minusDays(2).toString()).toString()
        )
    )

    fun getUniqueNames(): List<String> = dummyRecentCommits.map { it.name }.distinct()

    fun hasCommitsInWeek(): SnapshotStateList<Boolean> = List(7) { index ->
        val nDaysAgo = Date.valueOf(LocalDate.now().minusDays(index.toLong()).toString()).toString()
        dummyRecentCommits.any { it.date == nDaysAgo }
    }.reversed().toMutableStateList()

    fun hasCommitsInWeekAbout(name: String): SnapshotStateList<Boolean> = List(7) { index ->
        val nDaysAgo = Date.valueOf(LocalDate.now().minusDays(index.toLong()).toString()).toString()
        dummyRecentCommits.any { it.date == nDaysAgo && it.name == name }
    }.reversed().toMutableStateList()

}