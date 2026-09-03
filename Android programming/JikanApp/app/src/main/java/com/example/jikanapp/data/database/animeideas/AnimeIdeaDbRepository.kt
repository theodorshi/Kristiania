package com.example.jikanapp.data.database.animeideas

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.jikanapp.data.database.AppDatabase

object AnimeIdeaDbRepository {
    private lateinit var _appDatabase : AppDatabase
    private val _animeDao by lazy { _appDatabase.animeIdeaDao() }

    fun initializeDatabase(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "animeIdeas"
        ).build()
    }


    suspend fun getAllAnimes() : List<AnimeIdea> {
        try {
            return _animeDao.getAllAnimes()
        } catch (e: Exception) {
            Log.d("getAllAnimesCatch", e.toString())
            return emptyList()
        }
    }

    suspend fun getAnimeById(id: Int) : AnimeIdea? {
        try {
            return _animeDao.getAnimeById(id)
        } catch (e : Exception){
            Log.d("getAnimeByIdCatch", e.toString())
            return null
        }
    }

    suspend fun insertAnime(animeIdea: AnimeIdea) : Long {
        try {
            return _animeDao.insertAnime(animeIdea)
        } catch (e : Exception) {
            Log.d("insertAnimeCatch", e.toString())
            return -1L
        }
    }


    suspend fun deleteAnime(animeIdea: AnimeIdea) : Int {
        try {
          return _animeDao.deleteAnime(animeIdea)
        } catch (e : Exception) {
            Log.d("deleteAnimeCatch", e.toString())
            return -1
        }
    }

    suspend fun updateAnime(animeIdea: AnimeIdea) : Int{
        try {
           return _animeDao.updateAnime(animeIdea)
        } catch (e : Exception) {
            Log.d("updateAnimeCatch", e.toString())
            return -1
        }
    }
    suspend fun getAnimeByTitle(title: String) : List<AnimeIdea> {
        try {
            return _animeDao.getAnimeByTitle("%$title%")
        } catch (e: Exception) {
            Log.d("getAnimeByTitleCatch", e.toString())
            return emptyList()
        }
    }
}