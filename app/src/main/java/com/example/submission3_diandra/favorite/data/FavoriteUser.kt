package com.example.submission3_diandra.favorite.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class FavoriteUser(
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
    @PrimaryKey
    val id: Int

): Serializable