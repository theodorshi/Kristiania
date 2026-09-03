package com.example.jikanapp.screens.animesearch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jikanapp.data.api.Anime
import com.example.jikanapp.data.api.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeSearchViewModel : ViewModel() {
    private val _anime = MutableStateFlow<Anime?>(null)

    val anime = _anime.asStateFlow()

    private val _animesByScore = MutableStateFlow<List<Anime>>(emptyList())
    val animesByScore = _animesByScore.asStateFlow()

    fun setAnimeById(id: Int) {
        viewModelScope.launch {
            _anime.value = AnimeRepository.getAnimeById(id)
            Log.d("setAnimeById", _anime.value.toString())
        }
    }

    fun clearAnime() {
        _anime.value = null
    }

    fun setAnimesByScore(score: Double) {
        viewModelScope.launch {
            _animesByScore.value = AnimeRepository.getAnimesByScore(score) ?: emptyList()
        }
    }

    fun clearAnimesByScore() {
        _animesByScore.value = emptyList()
    }

}