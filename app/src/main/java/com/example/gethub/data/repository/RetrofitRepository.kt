package com.example.gethub.data.repository

import com.example.gethub.data.remote.api.ApiConfig
import com.example.gethub.data.remote.responses.DetailResponse
import com.example.gethub.data.remote.responses.FollowersFollowingResponseItem
import com.example.gethub.data.remote.responses.SearchResponse
import java.lang.Exception

class RetrofitRepository {
    suspend fun getSearchUser(name: String): SearchResponse? {
        try {
            val request = ApiConfig.getApiServices().getSearchUser(name)
            if (request.isSuccessful) {
                request.body()?.let {
                    return it
                }
            }
        } catch (ex: Exception) {
        }
        return null
    }

    suspend fun getDetailUser(intentUsername: String): DetailResponse? {
        try {
            val request = ApiConfig.getApiServices().getDetailUser(intentUsername)
            if (request.isSuccessful) {
                request.body()?.let {
                    return it
                }
            }
        } catch (ex: Exception) {
        }
        return null
    }

    suspend fun getFollowers(username: String): List<FollowersFollowingResponseItem>? {
        try {
            val request = ApiConfig.getApiServices().getFollowers(username)
            if (request.isSuccessful) {
                request.body()?.let {
                    return it
                }
            }
        } catch (ex: Exception) {
        }
        return null
    }

    suspend fun getFollowing(username: String): List<FollowersFollowingResponseItem>? {
        try {
            val request = ApiConfig.getApiServices().getFollowing(username)
            if (request.isSuccessful) {
                request.body()?.let {
                    return it
                }
            }
        } catch (ex: Exception) {
        }
        return null
    }
}