package org.dicoding.githubuser.utils

import org.dicoding.githubuser.BuildConfig
import org.dicoding.githubuser.models.DetailUserResponse
import org.dicoding.githubuser.models.ItemsItem
import org.dicoding.githubuser.models.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    suspend fun getSearchUsers(
        @Query("q") username: String
    ) : UserResponse

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    suspend fun getDetailUser(@Path("username") username: String): Response<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    suspend fun getFollowers(@Path("username") username: String): List<ItemsItem>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    suspend fun getFollowing(@Path("username") username: String): List<ItemsItem>
}