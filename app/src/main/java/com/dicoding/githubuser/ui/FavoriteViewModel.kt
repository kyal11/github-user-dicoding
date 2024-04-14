package com.dicoding.githubuser.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuser.data.FavoriteRepository
import com.dicoding.githubuser.data.local.entity.FavoriteUsers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    fun getAllFavoriteUsers() {
        viewModelScope.launch {
            favoriteRepository.getAllFavoriteUsers()
        }
    }
}