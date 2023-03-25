package org.dicoding.githubuser.utils

import org.dicoding.githubuser.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    suspend fun getSearchUsers(
        @Query("q") username: String
    ) : UserResponse

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    suspend fun getDetailUser(@Path("username") username: String): Response<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    suspend fun getFollowers(@Path("username") username: String): List<ItemsItem>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    suspend fun getFollowing(@Path("username") username: String): List<ItemsItem>
}