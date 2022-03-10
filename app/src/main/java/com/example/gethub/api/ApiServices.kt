package com.example.gethub.api

import com.example.gethub.responses.DetailResponse
import com.example.gethub.responses.FollowersFollowingResponseItem
import com.example.gethub.responses.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(@Query("q") username: String) : Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(@Path("username") username: String) : Call<DetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username") username: String) : Call<List<FollowersFollowingResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username: String) : Call<List<FollowersFollowingResponseItem>>
}