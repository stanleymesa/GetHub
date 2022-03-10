package com.example.gethub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gethub.api.ApiConfig
import com.example.gethub.responses.FollowersFollowingResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    private val _listFollowing = MutableLiveData<List<FollowersFollowingResponseItem>>()
    val listFollowing: LiveData<List<FollowersFollowingResponseItem>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiServices().getFollowing(username)
        client.enqueue(object : Callback<List<FollowersFollowingResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowersFollowingResponseItem>>,
                response: Response<List<FollowersFollowingResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        _listFollowing.value = it
                    }
                }
            }

            override fun onFailure(call: Call<List<FollowersFollowingResponseItem>>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }
}