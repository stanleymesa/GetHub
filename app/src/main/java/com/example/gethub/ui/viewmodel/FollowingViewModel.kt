package com.example.gethub.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gethub.data.repository.RetrofitRepository
import com.example.gethub.data.remote.responses.FollowersFollowingResponseItem
import com.example.gethub.util.EspressoUtil
import kotlinx.coroutines.launch

class FollowingViewModel(private val repository: RetrofitRepository): ViewModel() {

    private val _listFollowing = MutableLiveData<List<FollowersFollowingResponseItem>>()
    val listFollowing: LiveData<List<FollowersFollowingResponseItem>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowing(username: String) {
        viewModelScope.launch {
            EspressoUtil.increment()
            _isLoading.postValue(true)
            val request = repository.getFollowing(username)
            request?.let {
                _listFollowing.postValue(it)
            }
            _isLoading.postValue(false)
            EspressoUtil.decrement()
        }
    }
}