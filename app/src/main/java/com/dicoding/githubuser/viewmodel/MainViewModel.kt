package com.dicoding.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.data.response.SearchResponse
import com.dicoding.githubuser.data.retrofit.ApiConfig
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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    fun searchUser(username: String) {
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

}