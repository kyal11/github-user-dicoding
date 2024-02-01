package com.dicoding.githubuser.data.retrofit

import com.dicoding.githubuser.data.response.DetailResponse
import com.dicoding.githubuser.data.response.FollowResponseItem
import com.dicoding.githubuser.data.response.SearchResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") username: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/follower")
    fun getListFollower(
        @Path("username") username: String
    ): Call<List<FollowResponseItem>>

    @GET("users/{username}/following")
    fun getListFollowing(
        @Path("username") username: String
    ): Call<List<FollowResponseItem>>
}