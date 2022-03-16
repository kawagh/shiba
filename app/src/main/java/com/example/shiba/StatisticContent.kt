package com.example.shiba

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.shiba.network.UserInfoResponse

@Composable
fun StatisticContent(
    allCommitsCount: Int,
    commitCountMap: Map<String, Int>,
    githubUserInfo: UserInfoResponse
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Total Commits: $allCommitsCount",
            fontSize = 45.sp
        )
        commitCountMap.forEach { (name, count) ->
            Text(
                text = "$name: $count",
                fontSize = 45.sp
            )
        }
        githubUserInfoColumn(githubUserInfo = githubUserInfo)
    }
}

@Composable
fun githubUserInfoColumn(githubUserInfo: UserInfoResponse) {
    Column() {
        Text(
            text = "publicRepos: ${githubUserInfo.public_repos}",
            fontSize = 45.sp
        )
    }

}

@Preview
@Composable
fun PreviewStatisticContent() {
    StatisticContent(
        allCommitsCount = 4, mapOf("A" to 3, "B" to 4),
        UserInfoResponse(-1)
    )
}
