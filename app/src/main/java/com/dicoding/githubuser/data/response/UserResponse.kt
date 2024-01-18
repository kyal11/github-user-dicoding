package com.dicoding.githubuser.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    var username: String,

) : Parcelable
