package com.example.gethub.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.gethub.data.local.datastore.SettingPreferences
import kotlinx.coroutines.launch

class PreferencesViewModel(private val pref: SettingPreferences) : ViewModel() {

    fun getThemePref(): LiveData<Boolean> {
        return pref.getThemePref().asLiveData()
    }

    fun getThemeTitlePref(): LiveData<String> {
        return pref.getThemeTitlePref().asLiveData()
    }

    fun saveThemePref(isDarkMode: Boolean) {
        viewModelScope.launch {
            pref.saveThemePref(isDarkMode)
        }
    }

    fun saveThemeTitlePref(title: String) {
        viewModelScope.launch {
            pref.saveThemeTitlePref(title)
        }
    }
}