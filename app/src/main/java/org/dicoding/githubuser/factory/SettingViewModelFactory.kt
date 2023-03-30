package org.dicoding.githubuser.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.dicoding.githubuser.preferences.SettingPreferences
import org.dicoding.githubuser.viewModels.SettingViewModel

class SettingViewModelFactory (private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}