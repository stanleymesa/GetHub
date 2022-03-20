package com.example.gethub.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RoomViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: RoomViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): RoomViewModelFactory {
            if (INSTANCE == null) {
                synchronized(RoomViewModelFactory::class.java) {
                    INSTANCE = RoomViewModelFactory(application)
                }
            }
            return INSTANCE as RoomViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class : ${modelClass.name}")
    }
}