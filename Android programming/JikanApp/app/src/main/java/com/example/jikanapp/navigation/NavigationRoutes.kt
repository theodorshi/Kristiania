package com.example.jikanapp.navigation

import kotlinx.serialization.Serializable

sealed class NavigationRoutes {
    @Serializable
    object AnimesRoute : NavigationRoutes()

    @Serializable
    object AnimeSearchRoute : NavigationRoutes()

    @Serializable
    object AnimeIdeasRoute : NavigationRoutes()

    @Serializable
    object AnimeFavoritesRoute : NavigationRoutes()

    @Serializable
    data class AnimeDetailsRoute(
        val animeId: Int
    ) : NavigationRoutes()

    @Serializable
    data class AnimeUpdateRoute(
        val animeId: Int
    ) : NavigationRoutes()

}