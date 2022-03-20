package com.example.gethub.data.remote.api

import com.example.gethub.BuildConfig
import com.example.gethub.data.remote.responses.DetailResponse
import com.example.gethub.data.remote.responses.FollowersFollowingResponseItem
import com.example.gethub.data.remote.responses.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    suspend fun getSearchUser(@Query("q") username: String) : Response<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    suspend fun getDetailUser(@Path("username") username: String) : Response<DetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    suspend fun getFollowers(@Path("username") username: String) : Response<List<FollowersFollowingResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    suspend fun getFollowing(@Path("username") username: String) : Response<List<FollowersFollowingResponseItem>>
}