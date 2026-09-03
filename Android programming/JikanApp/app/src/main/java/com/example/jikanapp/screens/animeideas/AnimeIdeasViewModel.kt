package com.example.jikanapp.screens.animeideas

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jikanapp.data.database.animeideas.AnimeIdeaDbRepository
import com.example.jikanapp.data.database.animeideas.AnimeIdea
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeIdeasViewModel : ViewModel(){
    private val _animes = MutableStateFlow<List<AnimeIdea>>(emptyList())
    val animes = _animes.asStateFlow()

    private val _animeBySearchResult = MutableStateFlow<List<AnimeIdea>>(emptyList())
    val animeBySearchResult = _animeBySearchResult.asStateFlow()

    var dbStatusMessage by mutableStateOf("")

    fun setAnimes() {
        viewModelScope.launch(Dispatchers.IO) {
            _animes.value = AnimeIdeaDbRepository.getAllAnimes()
        }
    }

    fun insertAnime(animeIdea: AnimeIdea) {
        viewModelScope.launch(Dispatchers.IO) {
            val newAnimeId = AnimeIdeaDbRepository.insertAnime(animeIdea)
            if (newAnimeId != -1L) {
                val newAnime = animeIdea.copy(id = newAnimeId.toInt())
                _animes.value += newAnime
                dbStatusMessage = "Lagt til i databasen"
            } else {
                Log.d("insertAnimeElse", "Feil med insert")
                dbStatusMessage= "Kunne ikke legge til i databasen"
            }
        }
    }

    fun setAnimeByTitle(title: String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = AnimeIdeaDbRepository.getAnimeByTitle(title)
            _animeBySearchResult.value = result
        }
    }

    fun resetAnimeSearchByTitle() {
        _animeBySearchResult.value = emptyList()
    }

    fun resetDbStatusMessage() {
        dbStatusMessage = ""
    }
}