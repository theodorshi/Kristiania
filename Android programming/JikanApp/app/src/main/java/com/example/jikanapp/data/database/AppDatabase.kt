package com.example.jikanapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jikanapp.data.database.animefavorites.AnimeFavorite
import com.example.jikanapp.data.database.animefavorites.AnimeFavoriteDao
import com.example.jikanapp.data.database.animeideas.AnimeIdeaDao
import com.example.jikanapp.data.database.animeideas.AnimeIdea

@Database(
    entities = [AnimeIdea::class, AnimeFavorite::class],
    version = 16,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun animeIdeaDao(): AnimeIdeaDao
    abstract fun animeFavoriteDao(): AnimeFavoriteDao
}