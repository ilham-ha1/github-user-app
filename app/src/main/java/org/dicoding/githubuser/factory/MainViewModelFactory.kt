package org.dicoding.githubuser.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.dicoding.githubuser.preferences.SettingPreferences
import org.dicoding.githubuser.viewModels.MainViewModel

class MainViewModelFactory(private val application: Application,private val pref: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application,pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}