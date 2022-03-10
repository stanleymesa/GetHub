package com.example.gethub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gethub.api.ApiConfig
import com.example.gethub.responses.FollowersFollowingResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {
    private val _listFollowers = MutableLiveData<List<FollowersFollowingResponseItem>>()
    val listFollowers: LiveData<List<FollowersFollowingResponseItem>> = _listFollowers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiServices().getFollowers(username)
        client.enqueue(object : Callback<List<FollowersFollowingResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowersFollowingResponseItem>>,
                response: Response<List<FollowersFollowingResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        _listFollowers.value = it
                    }
                }
            }

            override fun onFailure(call: Call<List<FollowersFollowingResponseItem>>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }
}