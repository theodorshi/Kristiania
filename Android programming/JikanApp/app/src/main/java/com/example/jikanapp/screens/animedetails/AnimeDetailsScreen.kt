package com.example.jikanapp.screens.animedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jikanapp.components.AnimeItem

@Composable
fun AnimeDetailsScreen(
    animeDetailsViewModel: AnimeDetailsViewModel,
    navController: NavController,
    animeId: Int
) {
    val anime = animeDetailsViewModel.anime.collectAsState()

    LaunchedEffect(Unit) {
        animeDetailsViewModel.loadAnime(animeId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(200, 200, 200))
    ) {
        Text(
            "Detaljer",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        anime.value?.let {
            AnimeItem(
                it,
                goBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}