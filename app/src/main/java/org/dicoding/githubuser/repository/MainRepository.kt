package org.dicoding.githubuser.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.dicoding.githubuser.database.entity.FavoriteUser
import org.dicoding.githubuser.database.room.FavoriteUserDao
import org.dicoding.githubuser.database.room.FavoriteUserRoomDatabase
import org.dicoding.githubuser.models.DetailUserResponse
import org.dicoding.githubuser.models.ItemsItem
import org.dicoding.githubuser.utils.ApiConfig
import org.dicoding.githubuser.utils.ApiService

class MainRepository(application: Application) {
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

    private val _listItem = MutableLiveData<List<ItemsItem>>()
    val listItem = _listItem

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser = _detailUser

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isDataNotFound = MutableLiveData(false)
    val isDataNotFound: LiveData<Boolean> = _isDataNotFound

    suspend fun getSearchItem(q:String) {
        _isLoading.value = true
        try{
            val response = apiService?.getSearchUsers(q)
            val items = response?.items
            if (items.isNullOrEmpty()) {
                _isDataNotFound.value = true
            } else {
                _isDataNotFound.value = false
                _listItem.value = response.items
            }
        }catch (e:Exception){
            Log.d("DETAIL","getSearchItem is error")
        }finally {
            _isLoading.value = false
        }
    }

    suspend fun getDetailUser(username: String) {
            _isLoading.value = true
            try{
                val response = apiService?.getDetailUser(username)
                if (response != null) {
                    if (response.isSuccessful) {
                        _detailUser.value = response.body()
                    }
                }
            }catch (e:Exception){
                Log.d("DETAIL","getDetailUser is error")
            }finally {
                _isLoading.value = false
            }
    }

    fun addFavorite(id:Int, username:String, avatarUrl:String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                id,
                username,
                avatarUrl
            )
            favoriteUserDao?.addFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = favoriteUserDao?.check(id)


    fun removeFavorite(id:Int){
        CoroutineScope(Dispatchers.IO).launch{
            favoriteUserDao?.removeFavorite(id)
        }
    }
}