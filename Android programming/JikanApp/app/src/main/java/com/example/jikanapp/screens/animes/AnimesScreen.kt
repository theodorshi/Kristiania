package com.example.jikanapp.screens.animes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jikanapp.components.AnimeListItem
import com.example.jikanapp.data.database.animefavorites.AnimeFavorite
import com.example.jikanapp.navigation.NavigationRoutes


@Composable
fun AnimesScreen(
    animesViewModel: AnimesViewModel,
    navController: NavController
) {
    val animes = animesViewModel.animes.collectAsState()

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            "Alle animer",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        LazyColumn {
            items(animes.value) { anime ->
                AnimeListItem(
                    anime,
                    showAnime = {
                        navController.navigate(NavigationRoutes.AnimeDetailsRoute(anime.id))
                    },
                    // Oppretter AnimeFavorite-objekt med verdiene til Anime objekt fra api, for å kunne inserte i databasen
                    addFavorite = {
                        val animeFavorite = AnimeFavorite(
                            id = anime.id,
                            title = anime.title,
                            images = anime.images.jpg.image,
                            source = anime.source,
                            episodes = anime.episodes,
                            airing = anime.airing,
                            score = anime.score,
                            rank = anime.rank
                        )
                        animesViewModel.insertAnimeFavorite(animeFavorite)
                    },
                )
            }
        }
    }
}