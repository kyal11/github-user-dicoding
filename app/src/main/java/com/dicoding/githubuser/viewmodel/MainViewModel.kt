package com.dicoding.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.DetailResponse
import com.dicoding.githubuser.data.response.FollowResponseItem
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.data.response.SearchResponse
import com.dicoding.githubuser.data.retrofit.ApiConfig
import com.dicoding.githubuser.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainViewModel : ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _searchList = MutableLiveData<List<ItemsItem>>()
    val searchList: LiveData<List<ItemsItem>> = _searchList

    private val _userDetail = MutableLiveData<DetailResponse?>()
    val userDetail : LiveData<DetailResponse?> = _userDetail

    private val _followingUser = MutableLiveData<List<FollowResponseItem?>>()
    val followingUser : LiveData<List<FollowResponseItem?>> = _followingUser

    private val _followersUser = MutableLiveData<List<FollowResponseItem?>>()
    val followersUser : LiveData<List<FollowResponseItem?>> = _followersUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    init {
        searchUser()
    }
    fun searchUser(username: String =  "rizky") {
        try {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getSearchUsers(username)
            client.enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        _searchList.value = responseBody?.items as List<ItemsItem>?
                    }
                    else {
                        _snackbarText.value = Event(response.message())
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        } catch (e: Exception) {
            Log.d("Token e", e.toString())
        }
    }

    fun detailUser(username: String) {
        try {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getDetailUser(username)
            client.enqueue(object : Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        _userDetail.value = responseBody
                    } else {
                        _snackbarText.value = Event(response.message())
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        } catch (e: Exception) {
            Log.d("Token e", e.toString())
        }
    }

    fun followingUser(username: String) {
        try {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getListFollowing(username)
            client.enqueue(object : Callback<List<FollowResponseItem>>{
                override fun onResponse(
                    call: Call<List<FollowResponseItem>>,
                    response: Response<List<FollowResponseItem>>
                ) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        _followingUser.value = response.body()
                    } else {
                        _snackbarText.value = Event(response.message())
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure get Following: ${t.message.toString()}")
                }
            })
        } catch (e : Exception) {
            Log.d("Token e", e.toString())
        }
    }

    fun followersUser(username: String) {
        try {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getListFollower(username)
            client.enqueue(object : Callback<List<FollowResponseItem>>{
                override fun onResponse(
                    call: Call<List<FollowResponseItem>>,
                    response: Response<List<FollowResponseItem>>
                ) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        _followersUser.value = response.body()
                    } else {
                        _snackbarText.value = Event(response.message())
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure get Followers: ${t.message.toString()}")
                }
            })
        } catch (e : Exception) {
            Log.d("Token e", e.toString())
        }
    }
}