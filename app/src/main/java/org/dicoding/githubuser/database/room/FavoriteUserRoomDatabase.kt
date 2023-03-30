package org.dicoding.githubuser.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.dicoding.githubuser.database.entity.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 1, exportSchema = false)
abstract class FavoriteUserRoomDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavoriteUserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteUserRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteUserRoomDatabase::class.java, "user_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteUserRoomDatabase
        }
    }
}