package com.example.gethub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gethub.api.ApiConfig
import com.example.gethub.responses.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _searchUsers = MutableLiveData<SearchResponse>()
    val searchUsers: LiveData<SearchResponse> = _searchUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _textOnSubmit = MutableLiveData<String?>()
    val textOnSubmit: LiveData<String?> = _textOnSubmit

    init {
        setTextOnSubmit(null)
    }

    fun getSearchUser(name: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiServices().getSearchUser(name)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    response.body()?.let {
                        _searchUsers.value = it
                    }
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }

    fun setTextOnSubmit(textHint: String?) {
        _textOnSubmit.value = textHint
    }
}