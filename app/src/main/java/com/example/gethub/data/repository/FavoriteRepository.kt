package com.example.gethub.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.gethub.data.local.database.Favorite
import com.example.gethub.data.local.database.FavoriteDao
import com.example.gethub.data.local.database.FavoriteRoomDatabase

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    suspend fun insert(favorite: Favorite) = mFavoriteDao.insert(favorite)

    suspend fun delete(favorite: Favorite) = mFavoriteDao.delete(favorite)

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavorites()
}