package org.dicoding.githubuser.viewModels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.dicoding.githubuser.preferences.SettingPreferences
import org.dicoding.githubuser.repository.MainRepository

class MainViewModel(application: Application,private val pref: SettingPreferences): AndroidViewModel(application)  {
    private val mainRepository = MainRepository(application)

    val listItem = mainRepository.listItem
    val detailUser = mainRepository.detailUser
    val isLoading = mainRepository.isLoading

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