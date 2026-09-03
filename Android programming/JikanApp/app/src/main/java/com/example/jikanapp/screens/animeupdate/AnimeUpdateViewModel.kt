package com.example.jikanapp.screens.animeupdate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jikanapp.data.database.animeideas.AnimeIdeaDbRepository
import com.example.jikanapp.data.database.animeideas.AnimeIdea
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeUpdateViewModel : ViewModel() {
    private val _animeIdea = MutableStateFlow<AnimeIdea?>(null)

    val anime = _animeIdea.asStateFlow()

    fun loadAnime(animeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _animeIdea.value = AnimeIdeaDbRepository.getAnimeById(animeId)
        }
    }

    fun updateAnime(animeIdea: AnimeIdea){
        viewModelScope.launch(Dispatchers.IO) {
            AnimeIdeaDbRepository.updateAnime(animeIdea)
            _animeIdea.value = AnimeIdeaDbRepository.getAnimeById(animeIdea.id)
        }
    }

    fun deleteAnime(animeIdea: AnimeIdea){
        viewModelScope.launch(Dispatchers.IO) {
            AnimeIdeaDbRepository.deleteAnime(animeIdea)
        }
    }

}