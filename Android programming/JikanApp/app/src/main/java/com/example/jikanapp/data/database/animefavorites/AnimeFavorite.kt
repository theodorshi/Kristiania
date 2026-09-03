package com.example.jikanapp.data.database.animefavorites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnimeFavorite(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val images: String,
    val source: String,
    val episodes: Int? = null,
    val airing: Boolean,
    val score: Double,
    val rank: Int
)