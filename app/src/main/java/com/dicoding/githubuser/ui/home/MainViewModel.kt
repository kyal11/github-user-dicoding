package com.dicoding.githubuser.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.FavoriteRepository
import com.dicoding.githubuser.data.remote.response.ItemsItem
import com.dicoding.githubuser.data.remote.response.SearchResponse
import com.dicoding.githubuser.data.remote.retrofit.ApiConfig
import com.dicoding.githubuser.utils.Event
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    companion object {
        private const val TAG = "SearchViewModel"
    }

    private val _searchList = MutableLiveData<List<ItemsItem>>()
    val searchList: LiveData<List<ItemsItem>> = _searchList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    val themeSetting: Flow<Boolean> = favoriteRepository.getThemeSetting()
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
                        _searchList.value = responseBody.items as List<ItemsItem>?
                    }
                    else {
                        _snackbarText.value = Event("Failed to retrieve data")
                        Log.e(TAG, "onFailure: Failed to retrieve data")
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