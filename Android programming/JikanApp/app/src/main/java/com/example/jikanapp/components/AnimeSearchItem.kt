package com.example.jikanapp.components

import android.graphics.Paint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.jikanapp.data.api.Anime

@Composable
fun AnimeSearchItem(anime: Anime) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    anime.title + " (${anime.id})",
                    fontWeight = FontWeight.Bold
                )
                Text("Score: ${anime.score}")
                Text(
                    "Sendes: " + if (anime.airing) "Ja" else "Nei",
                    color = if (anime.airing) Color(0, 255, 0) else Color(255, 0, 0)
                )
                Text("Episoder: ${anime.episodes}")
                Text("Rank: ${anime.rank}")
                Text(anime.source)
            }
            AsyncImage(
                model = anime.images.jpg.image,
                contentDescription = "Bilde av ${anime.title}",
            )
        }
    }
}
