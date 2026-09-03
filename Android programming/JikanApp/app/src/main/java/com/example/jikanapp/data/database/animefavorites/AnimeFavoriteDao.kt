package com.example.jikanapp.data.database.animefavorites

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jikanapp.data.api.Anime
import com.example.jikanapp.data.database.animeideas.AnimeIdea

@Dao
interface AnimeFavoriteDao {
    @Query("SELECT * FROM AnimeFavorite")
    suspend fun getAllAnimes(): List<AnimeFavorite>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAnime(animeFavorite: AnimeFavorite) : Long

    @Delete
    suspend fun deleteAnime(animeFavorite: AnimeFavorite): Int

}