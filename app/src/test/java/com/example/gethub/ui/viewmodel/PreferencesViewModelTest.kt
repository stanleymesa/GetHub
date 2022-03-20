package com.example.gethub.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gethub.data.local.datastore.SettingPreferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class PreferencesViewModelTest {

    @get:Rule
    val instanceTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var preferencesViewModel: PreferencesViewModel
    private lateinit var settingPreferences: SettingPreferences

    private val dummyTheme = false
    private val dummyTitleTheme = "Dark Mode"


    @Before
    fun before() {
        settingPreferences = mock(SettingPreferences::class.java)
        preferencesViewModel = PreferencesViewModel(settingPreferences)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getThemePref() = runTest {

        val booleanFlow = flow {
            emit(dummyTheme)
        }

        `when`(settingPreferences.getThemePref()).thenReturn(booleanFlow)
        preferencesViewModel.getThemePref().observeForever {
            assertEquals(dummyTheme, it)
        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun getThemeTitlePref() = runTest {

        val stringFlow = flow {
            emit(dummyTitleTheme)
        }

        `when`(settingPreferences.getThemeTitlePref()).thenReturn(stringFlow)
        preferencesViewModel.getThemeTitlePref().observeForever {
            assertEquals(dummyTitleTheme, it)
        }

    }


}