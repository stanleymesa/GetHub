package com.example.gethub.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gethub.data.remote.responses.SearchResponse
import com.example.gethub.data.repository.RetrofitRepository
import com.example.gethub.util.EspressoUtil
import kotlinx.coroutines.launch

class MainViewModel(private val repository: RetrofitRepository) : ViewModel() {

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

        viewModelScope.launch {
            EspressoUtil.increment()
            _isLoading.postValue(true)
            val request = repository.getSearchUser(name)
            request?.let {
                _searchUsers.postValue(it)
            }
            _isLoading.postValue(false)
            EspressoUtil.decrement()
        }
    }

    fun setTextOnSubmit(textHint: String?) {
        _textOnSubmit.value = textHint
    }
}