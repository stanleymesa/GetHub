package com.example.gethub.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gethub.data.repository.RetrofitRepository
import com.example.gethub.data.remote.responses.FollowersFollowingResponseItem
import com.example.gethub.util.EspressoUtil
import kotlinx.coroutines.launch

class FollowersViewModel(private val repository: RetrofitRepository): ViewModel() {

    private val _listFollowers = MutableLiveData<List<FollowersFollowingResponseItem>>()
    val listFollowers: LiveData<List<FollowersFollowingResponseItem>> = _listFollowers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowers(username: String) {
        viewModelScope.launch {
            EspressoUtil.increment()
            _isLoading.postValue(true)
            val request = repository.getFollowers(username)
            request?.let {
                _listFollowers.postValue(it)
            }
            _isLoading.postValue(false)
            EspressoUtil.decrement()
        }
    }
}