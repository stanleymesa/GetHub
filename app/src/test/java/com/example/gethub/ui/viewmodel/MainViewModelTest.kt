package com.example.gethub.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gethub.data.remote.responses.ItemsItem
import com.example.gethub.data.remote.responses.SearchResponse
import com.example.gethub.data.repository.RetrofitRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MainViewModelTest {

    @get:Rule
    val instanceTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var repository: RetrofitRepository

    private val dummyName = "Joko"
    private val dummyAvatarUrl = "avatar.com"
    private val dummySearchResponse = SearchResponse(
        mutableListOf(
            ItemsItem(dummyAvatarUrl, dummyName)
        )
    )

    @Before
    fun before() {
        repository = mock(RetrofitRepository::class.java)
        mainViewModel = MainViewModel(repository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getSearchUsers() = runTest {
        `when`(repository.getSearchUser(dummyName)).thenReturn(dummySearchResponse)
        mainViewModel.getSearchUser(dummyName)
        mainViewModel.searchUsers.observeForever {
            assertEquals(dummySearchResponse, it)
        }
    }

}