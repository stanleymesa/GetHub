package com.example.gethub.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gethub.data.local.datastore.SettingPreferences

class PrefViewModelFactory private constructor(private val pref: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: PrefViewModelFactory? = null

        @JvmStatic
        fun getInstance(pref: SettingPreferences): PrefViewModelFactory {
            if (INSTANCE == null) {
                synchronized(PrefViewModelFactory::class.java) {
                    INSTANCE = PrefViewModelFactory(pref)
                }
            }
            return INSTANCE as PrefViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PreferencesViewModel::class.java)) {
            return PreferencesViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class : ${modelClass.name}")
    }
}