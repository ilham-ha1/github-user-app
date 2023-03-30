package org.dicoding.githubuser.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.dicoding.githubuser.preferences.SettingPreferences
import org.dicoding.githubuser.repository.MainRepository

class MainViewModel(application: Application,private val pref: SettingPreferences): AndroidViewModel(application)  {
    private val mainRepository = MainRepository(application)

    val listItem = mainRepository.listItem
    val detailUser = mainRepository.detailUser
    val isLoading = mainRepository.isLoading
    val isNotFound = mainRepository.isDataNotFound

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun getSearchItem(q:String) {
        viewModelScope.launch {
            mainRepository.getSearchItem(q)
        }
    }

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            mainRepository.getDetailUser(username)
        }
    }

    fun addFavorite(username:String, avatarUrl: String, id:Int){
        mainRepository.addFavorite(id,username,avatarUrl)
    }

    suspend fun checkUser(id: Int) = mainRepository.checkUser(id)

    fun removeFavorite(id:Int){
        mainRepository.removeFavorite(id)
    }
}