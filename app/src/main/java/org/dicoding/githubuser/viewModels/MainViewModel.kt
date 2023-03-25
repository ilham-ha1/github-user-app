package org.dicoding.githubuser.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.dicoding.githubuser.models.DetailUserResponse
import org.dicoding.githubuser.models.ItemsItem
import org.dicoding.githubuser.utils.ApiConfig

class MainViewModel:ViewModel() {

    private val apiService = ApiConfig.getApiService()

    private val _listItem = MutableLiveData<List<ItemsItem>>()
    val listItem = _listItem

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser = _detailUser

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSearchItem(q:String) {
        viewModelScope.launch {
            _isLoading.value = true
            try{
                val response = apiService.getSearchUsers(q)
                _listItem.value = response.items
            }catch (e:Exception){
                Log.d("DETAIL","getSearchItem is error")
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try{
                val response = apiService.getDetailUser(username)
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                }
            }catch (e:Exception){
                Log.d("DETAIL","getDetailUser is error")
            }finally {
                _isLoading.value = false
            }

        }
    }

}