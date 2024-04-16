package com.dicoding.githubuser.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuser.data.FavoriteRepository
import com.dicoding.githubuser.data.local.entity.FavoriteUsers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    private val _listFavorite = MutableLiveData<List<FavoriteUsers?>>()
    val listFavorite: LiveData<List<FavoriteUsers?>> = _listFavorite

    init {
        getAllFavoriteUsers()
    }

    fun getAllFavoriteUsers() {
        viewModelScope.launch {
            favoriteRepository.getAllFavoriteUsers().observeForever { favoriteUser ->
                _listFavorite.value = favoriteUser
            }
        }
    }
}