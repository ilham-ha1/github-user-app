package org.dicoding.githubuser.database.room

import androidx.lifecycle.LiveData
import androidx.room.*
import org.dicoding.githubuser.database.entity.FavoriteUser

@Dao
interface FavoriteUserDao {
   @Query("SELECT * FROM favorite_user")
   fun getFavoriteUser():LiveData<List<FavoriteUser>>

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun addFavorite(favoriteUser: FavoriteUser)

   @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
   suspend fun removeFavorite(id: Int):Int

   @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
   suspend fun check(id: Int): Int
}