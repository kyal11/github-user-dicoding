package com.dicoding.githubuser.data.retrofit

import com.dicoding.githubuser.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") usename: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<GithubResponse>

    @GET("users/{username}/follower")
    fun getListFollower(
        @Path("username") username: String
    ): Call<GithubResponse>

    @GET("users/{username}/following")
    fun getListFollowing(
        @Path("username") username: String
    ): Call<GithubResponse>
}