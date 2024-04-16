package com.dicoding.githubuser.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuser.data.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SettingViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    /**
     * Get theme setting from DataStore
     *
     * @return LiveData<Boolean>
     */
    val getThemeSetting: Flow<Boolean> = favoriteRepository.getThemeSetting()

    /**
     * Saving dark mode state to DataStore
     *
     * @param darkModeState Dark mode state
     */
    fun saveThemeSetting(darkModeState: Boolean) {
        viewModelScope.launch {
           favoriteRepository.saveThemeSetting(darkModeState)
        }
    }
}