package com.dicoding.githubuser.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dicoding.githubuser.data.FavoriteRepository
import com.dicoding.githubuser.data.local.room.FavoriteDatabase
import com.dicoding.githubuser.utils.AppExecutors
import com.dicoding.githubuser.utils.SettingPreferences

object Injection {
    fun provideRepository(context: Context,dataStore: DataStore<Preferences>) : FavoriteRepository {
        val database = FavoriteDatabase.getDatabase(context)
        val dao = database.favoriteDao()
        val appExecutors = AppExecutors()
        val preferences = SettingPreferences.getInstance(dataStore)

        return FavoriteRepository.getInstance(dao, appExecutors, preferences)
    }
}