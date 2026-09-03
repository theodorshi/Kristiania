package com.example.jikanapp.data.database.animefavorites

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.jikanapp.data.database.AppDatabase
import com.example.jikanapp.data.database.animeideas.AnimeIdea

object AnimeFavoriteDbRepository {
    private lateinit var _appDatabase: AppDatabase
    private val _animeFavoriteDao by lazy { _appDatabase.animeFavoriteDao() }

    fun initializeDatabase(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "animeIdeas"
        ).build()
    }

    suspend fun getAllAnimes(): List<AnimeFavorite> {
        try {
            return _animeFavoriteDao.getAllAnimes()
        } catch (e: Exception) {
            Log.d("getAllAnimesCatch", e.toString())
            return emptyList()
        }
    }

    suspend fun insertAnime(animeFavorite: AnimeFavorite) : Long {
        try {
            return _animeFavoriteDao.insertAnime(animeFavorite)
        } catch (e : Exception) {
            Log.d("insertAnimeCatch", e.toString())
            return -1L
        }
    }


    suspend fun deleteAnime(animeFavorite: AnimeFavorite) : Int {
        try {
            return _animeFavoriteDao.deleteAnime(animeFavorite)
        } catch (e : Exception) {
            Log.d("deleteAnimeCatch", e.toString())
            return -1
        }
    }

}