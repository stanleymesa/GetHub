package com.example.gethub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gethub.api.ApiConfig
import com.example.gethub.responses.DetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _detailUser = MutableLiveData<DetailResponse>()
    val detailUser: LiveData<DetailResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailUser(intentUsername: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiServices().getDetailUser(intentUsername)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        _detailUser.value = it
                    }
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }
}