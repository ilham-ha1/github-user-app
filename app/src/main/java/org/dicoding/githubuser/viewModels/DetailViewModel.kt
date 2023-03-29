package org.dicoding.githubuser.viewModels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.dicoding.githubuser.repository.DetailRepository
import org.dicoding.githubuser.database.room.FavoriteUserDao
import org.dicoding.githubuser.database.room.FavoriteUserRoomDatabase

class DetailViewModel(application: Application): AndroidViewModel(application) {
    private val detailRepository = DetailRepository(application)

    private var userDao: FavoriteUserDao?
    private var userDb: FavoriteUserRoomDatabase

    init{
        userDb = FavoriteUserRoomDatabase.getDatabase(application)
        userDao = userDb.favoriteUserDao()
    }

    val listFollower = detailRepository.listFollower
    val listFollowing = detailRepository.listFollowing
    val isLoading= detailRepository.isLoading

    fun getFollower(username:String){
        viewModelScope.launch {
            detailRepository.getFollower(username)
        }
    }

    fun getFollowing(username:String){
        viewModelScope.launch {
           detailRepository.getFollowing(username)
        }
    }





}