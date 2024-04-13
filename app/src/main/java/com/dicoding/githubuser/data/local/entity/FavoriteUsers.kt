package com.dicoding.githubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteUsers (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    val username: String = "",

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String? = null
) : Parcelable