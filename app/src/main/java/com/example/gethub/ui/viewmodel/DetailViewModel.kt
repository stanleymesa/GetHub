package com.example.gethub.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gethub.data.repository.RetrofitRepository
import com.example.gethub.data.remote.responses.DetailResponse
import com.example.gethub.util.EspressoUtil
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: RetrofitRepository) : ViewModel() {

    private val _detailUser = MutableLiveData<DetailResponse>()
    val detailUser: LiveData<DetailResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailUser(intentUsername: String) {
        viewModelScope.launch {
            EspressoUtil.increment()
            _isLoading.postValue(true)
            val request = repository.getDetailUser(intentUsername)
            request?.let {
                _detailUser.postValue(it)
            }
            _isLoading.postValue(false)
            EspressoUtil.decrement()
        }
    }
}