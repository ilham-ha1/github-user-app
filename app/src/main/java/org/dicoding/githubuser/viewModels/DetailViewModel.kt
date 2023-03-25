package org.dicoding.githubuser.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.dicoding.githubuser.models.ItemsItem
import org.dicoding.githubuser.utils.ApiConfig

class DetailViewModel:ViewModel() {

    private val apiService = ApiConfig.getApiService()

    private val _listFollower = MutableLiveData<List<ItemsItem>>()
    val listFollower = _listFollower

    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing = _listFollowing

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollower(username:String){
        viewModelScope.launch {
            _isLoading.value = true
            try{
                val response = apiService.getFollowers(username)
                _listFollower.postValue(response)
            }catch (e:Exception){
                Log.d("DETAIL","getFollower is error")
            }finally {
                _isLoading.value = false
            }
        }
    }


    fun getFollowing(username:String){
        viewModelScope.launch {
            _isLoading.value = true
            try{
                val response = apiService.getFollowing(username)
                _listFollowing.postValue(response)
            }catch (e:Exception){
                Log.d("DETAIL","getFollowing is error")
            }finally {
                _isLoading.value = false
            }
        }
    }

}