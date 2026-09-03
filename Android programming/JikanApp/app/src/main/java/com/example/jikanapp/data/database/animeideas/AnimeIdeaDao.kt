package com.example.jikanapp.data.database.animeideas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AnimeIdeaDao {
    @Query("SELECT * FROM AnimeIdea")
    suspend fun getAllAnimes(): List<AnimeIdea>

    @Query("SELECT * FROM AnimeIdea WHERE id = :id")
    suspend fun getAnimeById(id: Int) : AnimeIdea?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAnime(animeIdea: AnimeIdea) : Long

    @Delete
    suspend fun deleteAnime(animeIdea: AnimeIdea): Int

    @Update
    suspend fun updateAnime(animeIdea: AnimeIdea): Int

    @Query("SELECT * FROM AnimeIdea WHERE title LIKE :title")
    suspend fun getAnimeByTitle(title: String): List<AnimeIdea>

}