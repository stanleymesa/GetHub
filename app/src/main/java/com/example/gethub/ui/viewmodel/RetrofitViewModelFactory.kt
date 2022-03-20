package com.example.gethub.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gethub.data.repository.RetrofitRepository

class RetrofitViewModelFactory private constructor(private val repository: RetrofitRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: RetrofitViewModelFactory? = null

        @JvmStatic
        fun getInstance(repository: RetrofitRepository): RetrofitViewModelFactory {
            if (INSTANCE == null) {
                synchronized(RetrofitViewModelFactory::class.java) {
                    INSTANCE = RetrofitViewModelFactory(repository)
                }
            }
            return INSTANCE as RetrofitViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FollowersViewModel::class.java) -> {
                FollowersViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FollowingViewModel::class.java) -> {
                FollowingViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class : ${modelClass.name}")
        }
    }
}