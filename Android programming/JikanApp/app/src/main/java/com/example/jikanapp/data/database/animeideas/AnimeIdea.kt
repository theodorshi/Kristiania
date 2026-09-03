package com.example.jikanapp.data.database.animeideas

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnimeIdea(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val source: String,
    val episodes: Int? = null,
    val description: String,
    val mainCharacter: String? = null
)