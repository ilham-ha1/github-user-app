package org.dicoding.githubuser.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.dicoding.githubuser.viewModels.FavoriteUserAddViewModel

class FavoriteUserViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteUserAddViewModel::class.java)) {
            return FavoriteUserAddViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}