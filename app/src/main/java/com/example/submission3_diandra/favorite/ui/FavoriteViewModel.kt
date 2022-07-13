package com.example.submission3_diandra.favorite.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.submission3_diandra.favorite.data.DatabaseFavorite
import com.example.submission3_diandra.favorite.data.FavoriteUser
import com.example.submission3_diandra.favorite.data.FavoriteUserDao

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDao: FavoriteUserDao?
    private var userDb: DatabaseFavorite?

    init {
        userDb = DatabaseFavorite.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>?{
        return userDao?.getFavoriteUser()
    }
}