package com.dicoding.githubuser.data.retrofit

import com.dicoding.githubuser.data.response.DetailResponse
import com.dicoding.githubuser.data.response.FollowerResponse
import com.dicoding.githubuser.data.response.FollowingResponse
import com.dicoding.githubuser.data.response.SearchResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") usename: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/follower")
    fun getListFollower(
        @Path("username") username: String
    ): Call<FollowerResponse>

    @GET("users/{username}/following")
    fun getListFollowing(
        @Path("username") username: String
    ): Call<FollowingResponse>
}