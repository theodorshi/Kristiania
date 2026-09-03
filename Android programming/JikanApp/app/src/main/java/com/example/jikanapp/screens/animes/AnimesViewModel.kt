package com.example.jikanapp.screens.animes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jikanapp.data.api.Anime
import com.example.jikanapp.data.api.AnimeRepository
import com.example.jikanapp.data.database.animefavorites.AnimeFavorite
import com.example.jikanapp.data.database.animefavorites.AnimeFavoriteDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.plus

class AnimesViewModel : ViewModel() {
    private val _animes = MutableStateFlow<List<Anime>>(emptyList())
    val animes = _animes.asStateFlow()

    private val _animeFavorites = MutableStateFlow<List<AnimeFavorite>>(emptyList())

    fun setAnimes() {
        viewModelScope.launch {
            _animes.value = AnimeRepository.getAllAnimes()
        }
    }

    init {
        setAnimes()
    }

    fun insertAnimeFavorite(animeFavorite: AnimeFavorite) {
        viewModelScope.launch(Dispatchers.IO) {
            val newAnimeId = AnimeFavoriteDbRepository.insertAnime(animeFavorite)
            if (newAnimeId != -1L) {
                val newAnime = animeFavorite.copy(id = newAnimeId.toInt())
                _animeFavorites.value += newAnime
            } else {
                Log.d("insertAnimeElse", "Feil med insert")
            }
        }
    }
}