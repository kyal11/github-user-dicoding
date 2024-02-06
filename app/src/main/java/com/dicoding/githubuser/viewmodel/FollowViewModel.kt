package com.dicoding.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.FollowResponseItem
import com.dicoding.githubuser.data.retrofit.ApiConfig
import com.dicoding.githubuser.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class FollowViewModel : ViewModel() {
    companion object {
        private const val TAG = "FollowViewModel"
    }
    private val _followingUser = MutableLiveData<List<FollowResponseItem?>>()
    val followingUser : LiveData<List<FollowResponseItem?>> = _followingUser

    private val _followersUser = MutableLiveData<List<FollowResponseItem?>>()
    val followersUser : LiveData<List<FollowResponseItem?>> = _followersUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText
    fun followingUser(username: String) {
        try {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getListFollowing(username)
            client.enqueue(object : Callback<List<FollowResponseItem>> {
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
            client.enqueue(object : Callback<List<FollowResponseItem>> {
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
