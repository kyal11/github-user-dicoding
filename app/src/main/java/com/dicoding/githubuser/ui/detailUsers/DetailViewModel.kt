package com.dicoding.githubuser.ui.detailUsers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuser.data.FavoriteRepository
import com.dicoding.githubuser.data.local.entity.FavoriteUsers
import com.dicoding.githubuser.data.remote.response.DetailResponse
import com.dicoding.githubuser.data.remote.retrofit.ApiConfig
import com.dicoding.githubuser.utils.Event
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class DetailViewModel(private val favoriteRepository: FavoriteRepository): ViewModel() {

    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val _userDetail = MutableLiveData<DetailResponse?>()
    val userDetail: LiveData<DetailResponse?> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    // LiveData for favorite user
    private val _favoriteUser = MutableLiveData<FavoriteUsers?>()
    val favoriteUser: LiveData<FavoriteUsers?> = _favoriteUser

    var detailUsername: String = ""
        set(value) {
            field = value
            detailUser(detailUsername)
        }

    fun insertFavoriteUsers(favoriteUsers: FavoriteUsers) {
        viewModelScope.launch {
            favoriteRepository.insert(favoriteUsers)
        }
    }

    fun getFavoriteByUsername(username: String) {
        viewModelScope.launch {
            favoriteRepository.getFavoriteUserByUsername(username).observeForever { favoriteUser ->
                _favoriteUser.value = favoriteUser
            }
        }
    }

    fun deleteFavoriteUsers(favoriteUsers: FavoriteUsers) {
        viewModelScope.launch {
            favoriteRepository.delete(favoriteUsers)
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
                        _snackbarText.value = Event("Failed to retrieve data")
                        Log.e(TAG, "onFailure: Failed to retrieve data")
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
}
