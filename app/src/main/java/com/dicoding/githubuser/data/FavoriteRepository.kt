package com.dicoding.githubuser.data

import androidx.lifecycle.LiveData
import com.dicoding.githubuser.data.local.room.FavoriteDao
import com.dicoding.githubuser.data.local.entity.FavoriteUsers
import com.dicoding.githubuser.utils.AppExecutors

class FavoriteRepository private constructor(
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors
) {

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUsers>>  = favoriteDao.getAllFavoriteUsers()

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUsers>  = favoriteDao.getFavoriteByUsername(username)
    fun delFavoriteByUsername(username: String) = favoriteDao.delFavoriteByUsername(username)

    fun insert(favoriteUsers: FavoriteUsers) {
        appExecutors.diskID.execute {
            favoriteDao.insert(favoriteUsers)
        }
    }

    fun update(favoriteUsers: FavoriteUsers) {
        appExecutors.diskID.execute {
            favoriteDao.update(favoriteUsers)
        }
    }

    fun delete(favoriteUsers: FavoriteUsers) {
        appExecutors.diskID.execute {
            favoriteDao.delete(favoriteUsers)
        }
    }

    companion object {
        private var INSTANCE: FavoriteRepository? = null

        fun getInstance(
            favoriteDao: FavoriteDao,
            appExecutors: AppExecutors
        ): FavoriteRepository {
            return  INSTANCE ?: synchronized(this) {
                FavoriteRepository(favoriteDao, appExecutors).also {
                    INSTANCE = it
                }
            }
        }
    }
}