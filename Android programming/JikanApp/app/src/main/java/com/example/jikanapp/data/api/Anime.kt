package com.example.jikanapp.data.api

import com.google.gson.annotations.SerializedName

data class Anime(
    @SerializedName("mal_id")
    val id: Int,
    val title: String,
    val images: Images,
    val source: String,
    val episodes: Int?,
    val airing: Boolean,
    val score: Double,
    val rank: Int
)

data class Images(
    val jpg: jpgs
)

data class jpgs(
    @SerializedName("image_url")
    val image: String,
    @SerializedName("small_image_url")
    val smallImage: String,
    @SerializedName("large_image_url")
    val largeImage: String
)
