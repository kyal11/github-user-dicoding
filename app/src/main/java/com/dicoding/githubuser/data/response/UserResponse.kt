package com.dicoding.githubuser.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    @SerializedName("login") val username: String?= null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("avatar_url") val avatar: String? = null,
    @SerializedName("public_repos") val repository: Int? = null,
    @SerializedName("followers") val follower: Int? = null,
    @SerializedName("following") val following: Int? = null
) : Parcelable
