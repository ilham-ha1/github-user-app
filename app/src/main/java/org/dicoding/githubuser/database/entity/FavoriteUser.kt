package org.dicoding.githubuser.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class FavoriteUser(
    @PrimaryKey
    @field:ColumnInfo(name = "id")
    var id: Int,

    @field:ColumnInfo(name = "username")
    var username: String = "",

    @field:ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null,

) : Serializable