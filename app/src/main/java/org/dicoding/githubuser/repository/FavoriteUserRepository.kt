package org.dicoding.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import org.dicoding.githubuser.database.entity.FavoriteUser
import org.dicoding.githubuser.database.room.FavoriteUserDao
import org.dicoding.githubuser.database.room.FavoriteUserRoomDatabase

class FavoriteUserRepository(application: Application) {
    private var favoriteUserDao: FavoriteUserDao?
    private var userDb: FavoriteUserRoomDatabase?

    init {
        userDb = FavoriteUserRoomDatabase.getDatabase(application)
        favoriteUserDao = userDb?.favoriteUserDao()
    }

    fun getFavorite(): LiveData<List<FavoriteUser>>?{
        return favoriteUserDao?.getFavoriteUser()
    }



}