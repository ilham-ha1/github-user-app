package org.dicoding.githubuser.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.dicoding.githubuser.database.room.FavoriteUserDao
import org.dicoding.githubuser.database.room.FavoriteUserRoomDatabase
import org.dicoding.githubuser.models.ItemsItem
import org.dicoding.githubuser.utils.ApiConfig
import org.dicoding.githubuser.utils.ApiService

class DetailRepository(application: Application){
    private var favoriteUserDao: FavoriteUserDao?
    private var userDb: FavoriteUserRoomDatabase?

    private var apiService: ApiService? = null

    private fun setMyDependency(apiService: ApiService) {
        this.apiService = apiService
    }

    init {
        setMyDependency(ApiConfig.getApiService())
        userDb = FavoriteUserRoomDatabase.getDatabase(application)
        favoriteUserDao = userDb?.favoriteUserDao()
    }

    private val _listFollower = MutableLiveData<List<ItemsItem>>()
    val listFollower = _listFollower

    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing = _listFollowing

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun getFollower(username:String){
            _isLoading.value = true
            try{
                val response = apiService?.getFollowers(username)
                _listFollower.postValue(response!!)
            }catch (e:Exception){
                Log.d("DETAIL","getFollower is error")
            }finally {
                _isLoading.value = false
            }
    }


    suspend fun getFollowing(username:String){
            _isLoading.value = true
            try{
                val response = apiService?.getFollowing(username)
                _listFollowing.postValue(response!!)
            }catch (e:Exception){
                Log.d("DETAIL","getFollowing is error")
            }finally {
                _isLoading.value = false
            }
    }




}