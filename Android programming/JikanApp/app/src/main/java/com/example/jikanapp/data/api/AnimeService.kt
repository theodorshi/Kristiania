package com.example.jikanapp.data.api


import retrofit2.http.Query
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface AnimeService {
    @GET("anime")
    suspend fun getAllAnimes(): Response<Animes>

    @GET("anime/{mal_id}")
    suspend fun getAnimeById(@Path("mal_id") id: Int): Response<AnimeData>


    // Benytter query for å hente liste med objekter fra Jikan API basert på en minimumscore
    // https://docs.api.jikan.moe/#/anime/getanimesearch
    @GET("anime")
    suspend fun getAnimesByScore(
        @Query("min_score") minScore: Double
    ): Response<Animes>
}