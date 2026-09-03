package com.example.jikanapp.screens.animedetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jikanapp.data.api.Anime
import com.example.jikanapp.data.api.AnimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeDetailsViewModel : ViewModel() {
    private val _anime = MutableStateFlow<Anime?>(null)

    val anime = _anime.asStateFlow()

    fun loadAnime(animeId: Int) {
        viewModelScope.launch {
            _anime.value = AnimeRepository.getAnimeById(animeId)
        }
    }
}
