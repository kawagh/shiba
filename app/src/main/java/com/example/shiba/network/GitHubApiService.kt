package com.example.shiba.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://api.github.com"

data class UserInfoResponse(val public_repos: Int)

interface GitHubApiService {
    @GET("users/kawagh")
    fun fetchUserInfo(): Call<UserInfoResponse>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object GithubApi {
    val retrofitService: GitHubApiService by lazy { retrofit.create(GitHubApiService::class.java) }
}