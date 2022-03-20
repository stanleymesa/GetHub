package com.example.gethub.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>){
    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    private val keyTheme = booleanPreferencesKey("key_theme")
    private val keyThemeTitle = stringPreferencesKey("key_theme_title")

    fun getThemePref(): Flow<Boolean> {
        return dataStore.data.map {
            it[keyTheme] ?: false
        }
    }

    fun getThemeTitlePref(): Flow<String> {
        return dataStore.data.map {
            it[keyThemeTitle] ?: ""
        }
    }

    suspend fun saveThemePref(isDarkMode: Boolean) {
        dataStore.edit {
            it[keyTheme] = isDarkMode
        }
    }

    suspend fun saveThemeTitlePref(title: String) {
        dataStore.edit {
            it[keyThemeTitle] = title
        }
    }
}