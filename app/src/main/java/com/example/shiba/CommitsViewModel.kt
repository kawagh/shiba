package com.example.shiba

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shiba.network.GithubApi
import com.example.shiba.network.UserInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await
import java.lang.Exception
import java.sql.Date
import java.time.LocalDate

class CommitsViewModel(application: Application) : AndroidViewModel(application) {
    private val db: AppDatabase = AppDatabase.getInstance(application)
    internal val commitsInDatabase: LiveData<List<Commit>> = db.commitDao().getAll()

    private val _response = MutableLiveData<UserInfoResponse>()

    // The external immutable LiveData for the request status
    val response: LiveData<UserInfoResponse> = _response

    init {
        getGitHubUserInfo()
    }

    private fun getGitHubUserInfo() {
        viewModelScope.launch {
            try {
                val result = GithubApi.retrofitService.fetchUserInfo().await()
                _response.value = result
            } catch (e: Exception) {
                UserInfoResponse(-1).also { _response.value = it }
            }
        }

    }


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


    fun insert(commit: Commit) {
        viewModelScope.launch(Dispatchers.IO) {
            db.commitDao().insert(commit)
        }
    }

    fun deleteCommitsAbout(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            db.commitDao().deleteCommitsAbout(name)
        }
    }

    fun clear() {
        viewModelScope.launch(Dispatchers.IO) {
            db.commitDao().deleteAll()
        }
    }

    fun getCommitIds(): List<String>? =
        commitsInDatabase.value?.map { it.id.toString() }?.distinct()

    fun hasCommitsInWeek(): SnapshotStateList<Boolean> = List(7) { index ->
        val nDaysAgo = Date.valueOf(LocalDate.now().minusDays(index.toLong()).toString()).toString()
        dummyRecentCommits.any { it.date == nDaysAgo }
    }.reversed().toMutableStateList()
}

const val DAYS_IN_A_WEEK: Int = 7

fun State<List<Commit>>.toRecentProgress(): SnapshotStateList<Int> = List(DAYS_IN_A_WEEK) { index ->
    val daysWithCommits = this.value.map { it.date }
    daysWithCommits.count { it == LocalDate.now().minusDays((6 - index).toLong()).toString() }
}.toMutableStateList()

fun State<List<Commit>>.toRecentProgressAbout(name: String): SnapshotStateList<Int> =
    List(DAYS_IN_A_WEEK) { index ->
        val daysWithCommits = this.value.filter { it.name == name }.map { it.date }
        daysWithCommits.count {
            it == LocalDate.now().minusDays((DAYS_IN_A_WEEK - 1 - index).toLong()).toString()
        }
    }.toMutableStateList()
