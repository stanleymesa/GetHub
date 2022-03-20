package com.example.gethub.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gethub.data.local.database.Favorite
import com.example.gethub.data.repository.FavoriteRepository
import com.example.gethub.util.EspressoUtil
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository = FavoriteRepository(application)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorites()

    fun insert(favorite: Favorite) {
        viewModelScope.launch {
            EspressoUtil.increment()
            _isLoading.postValue(true)
            mFavoriteRepository.insert(favorite)
            _isLoading.postValue(false)
            EspressoUtil.decrement()
        }
    }

    fun delete(favorite: Favorite) {
        viewModelScope.launch {
            EspressoUtil.increment()
            _isLoading.postValue(true)
            mFavoriteRepository.delete(favorite)
            _isLoading.postValue(false)
            EspressoUtil.decrement()
        }
    }

}