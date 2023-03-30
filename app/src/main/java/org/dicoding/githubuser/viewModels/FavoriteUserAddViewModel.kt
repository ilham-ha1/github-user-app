package org.dicoding.githubuser.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import org.dicoding.githubuser.database.entity.FavoriteUser
import org.dicoding.githubuser.repository.FavoriteUserRepository
import org.dicoding.githubuser.database.room.FavoriteUserDao
import org.dicoding.githubuser.database.room.FavoriteUserRoomDatabase

class FavoriteUserAddViewModel(application: Application):AndroidViewModel(application) {
    private val favoriteUserRepository = FavoriteUserRepository(application)

    private var userDao: FavoriteUserDao?
    private var userDb: FavoriteUserRoomDatabase

    init{
        userDb = FavoriteUserRoomDatabase.getDatabase(application)
        userDao = userDb.favoriteUserDao()
    }

    private val _listItem = MutableLiveData<List<FavoriteUser>>()
    val listItem = _listItem

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFavorite(){
        _isLoading.value = true
        try{
            favoriteUserRepository.getFavorite()?.observeForever { favoriteUsers ->
                val user = favoriteUsers.map {
                    FavoriteUser(
                        it.id,
                        it.username,
                        it.avatarUrl
                    )
                }
                _listItem.value = user
            }
        }catch (e:Exception){
            Log.d("Favorite","getFavorite is error")
        }finally {
            _isLoading.value = false
        }
    }


}