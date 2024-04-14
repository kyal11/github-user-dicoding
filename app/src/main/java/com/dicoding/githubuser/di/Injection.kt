package com.dicoding.githubuser.di

import android.content.Context
import com.dicoding.githubuser.data.FavoriteRepository
import com.dicoding.githubuser.data.local.room.FavoriteDatabase
import com.dicoding.githubuser.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context) : FavoriteRepository {
        val database = FavoriteDatabase.getDatabase(context)
        val dao = database.favoriteDao()
        val appExecutors = AppExecutors()

        return FavoriteRepository.getInstance(dao, appExecutors)
    }
}