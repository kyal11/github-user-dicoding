package com.dicoding.githubuser.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.githubuser.data.local.room.FavoriteDao
import com.dicoding.githubuser.data.local.room.FavoriteDatabase
import com.dicoding.githubuser.data.local.entity.FavoriteUsers
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUsers>> = mFavoriteDao.getAllFavoriteUsers()

    fun getFavoriteUserByUsername(username: String): LiveData<List<FavoriteUsers>> = mFavoriteDao.getFavoriteByUsername(username)

    fun delFavoriteByUsername(username: String): LiveData<List<FavoriteUsers>> = mFavoriteDao.delFavoriteByUsername(username)
    fun insert(favoriteUsers: FavoriteUsers) {
        executorService.execute { mFavoriteDao.insert(favoriteUsers)}
    }

    fun update(favoriteUsers: FavoriteUsers) {
        executorService.execute{ mFavoriteDao.update(favoriteUsers)}
    }

    fun delete(favoriteUsers: FavoriteUsers) {
        executorService.execute{ mFavoriteDao.delete(favoriteUsers)}
    }

}