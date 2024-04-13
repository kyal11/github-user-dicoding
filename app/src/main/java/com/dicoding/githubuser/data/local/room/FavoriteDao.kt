package com.dicoding.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dicoding.githubuser.data.local.entity.FavoriteUsers

interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUsers: FavoriteUsers)

    @Update
    fun update(favoriteUsers: FavoriteUsers)

    @Delete
    fun delete(favoriteUsers: FavoriteUsers)

    @Query("DELETE FROM favoriteusers WHERE username = :username")
    fun delFavoriteByUsername(username: String): LiveData<List<FavoriteUsers>>

    @Query("SELECT * FROM favoriteusers ORDER BY username ASC")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUsers>>

    @Query("SELECT * FROM favoriteusers WHERE username = :username")
    fun getFavoriteByUsername(username: String): LiveData<List<FavoriteUsers>>
}