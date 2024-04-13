package com.dicoding.githubuser.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.FavoriteRepository
import com.dicoding.githubuser.data.local.entity.FavoriteUsers

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favoriteUsers: FavoriteUsers) {
        mFavoriteRepository.insert(favoriteUsers)
    }

    fun update(favoriteUsers: FavoriteUsers) {
        mFavoriteRepository.update(favoriteUsers)
    }

    fun delete(favoriteUsers: FavoriteUsers) {
        mFavoriteRepository.delete(favoriteUsers)
    }
}