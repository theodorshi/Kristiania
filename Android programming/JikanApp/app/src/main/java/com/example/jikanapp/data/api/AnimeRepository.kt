package com.example.jikanapp.data.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AnimeRepository {
    private val _httpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
        ).build()

    private val _retrofit = Retrofit.Builder()
        .client(_httpClient)
        .baseUrl("https://api.jikan.moe/v4/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val _animeService = _retrofit.create(AnimeService::class.java)

    // Metoder
    suspend fun getAllAnimes() : List<Anime> {
        try {
            val response = _animeService.getAllAnimes()

            return if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else {
                Log.d("getAllAnimesElse", response.toString())
                emptyList()
            }
        } catch (e: Exception) {
            Log.d("getAllAnimesCatch", e.message.toString())
            return emptyList()
        }
    }

    suspend fun getAnimeById(id: Int) : Anime? {
        try {
            val response = _animeService.getAnimeById(id)

            return if(response.isSuccessful) {
                response.body()?.data
            } else {
                return null
            }
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun getAnimesByScore(score: Double) : List<Anime>? {
        try {
            val response = _animeService.getAnimesByScore(score)

            return if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e : Exception) {
            return emptyList()
        }
    }



}
