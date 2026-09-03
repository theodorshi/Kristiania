package com.example.jikanapp.screens.animefavorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jikanapp.data.database.animefavorites.AnimeFavorite
import com.example.jikanapp.data.database.animefavorites.AnimeFavoriteDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.plus

class AnimeFavoritesViewModel : ViewModel() {
    private val _animeFavorites = MutableStateFlow<List<AnimeFavorite>>(emptyList())
    val animeFavorites = _animeFavorites.asStateFlow()

    fun setAnimeFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            _animeFavorites.value = AnimeFavoriteDbRepository.getAllAnimes()
        }
    }

    fun deleteAnime(animeFavorite: AnimeFavorite) {
        viewModelScope.launch(Dispatchers.IO) {
            AnimeFavoriteDbRepository.deleteAnime(animeFavorite)
            setAnimeFavorites()
        }
    }
}
