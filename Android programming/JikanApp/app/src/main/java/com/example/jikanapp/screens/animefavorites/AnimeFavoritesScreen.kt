package com.example.jikanapp.screens.animefavorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jikanapp.components.AnimeFavoriteItem
import com.example.jikanapp.components.AnimeListItem
import com.example.jikanapp.navigation.NavigationRoutes

@Composable
fun AnimeFavoritesScreen(
    animeFavoritesViewModel: AnimeFavoritesViewModel,
    navController: NavController
) {
    val animeFavorites = animeFavoritesViewModel.animeFavorites.collectAsState()


    LaunchedEffect(Unit) {
        animeFavoritesViewModel.setAnimeFavorites()
    }

    Column (
        modifier = Modifier.fillMaxSize()
    ){
        Text("Favoritter",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center)

        LazyColumn {
            items(animeFavorites.value) {
                anime ->
                AnimeFavoriteItem(
                    animeFavorite = anime,
                    showAnime = {navController.navigate(NavigationRoutes.AnimeDetailsRoute(anime.id))},
                    removeFavorite = {animeFavoritesViewModel.deleteAnime(anime)}
                )

            }
        }
    }
}
