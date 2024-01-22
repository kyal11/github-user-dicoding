package com.dicoding.githubuser.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    var username: String,
    var name: String,
    var avatar: String,
    var repository: Int,
    var follower: Int,
    var following: Int
) : Parcelable
