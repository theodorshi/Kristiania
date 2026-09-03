package com.example.jikanapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.jikanapp.data.api.Anime


@Composable
fun AnimeItem(
    anime: Anime,
    goBack: () -> Unit
) {
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                anime.title + " (${anime.id})",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            AsyncImage(
                model = anime.images.jpg.largeImage,
                contentDescription = "Bilde av ${anime.title}",
                modifier = Modifier
                    .padding(vertical = 16.dp)
            )
            Text(
                "Score: ${anime.score}",
                fontSize = 20.sp
            )
            Text(
                "Sendes: " + if (anime.airing) "Ja" else "Nei",
                color = if (anime.airing) Color(180, 220, 90) else Color(180, 60, 60),
                fontSize = 20.sp
            )
            Text("Episoder: ${anime.episodes}", fontSize = 20.sp)
            Text("Rank: ${anime.rank}", fontSize = 20.sp)
            Text(anime.source, fontSize = 20.sp)
        }
    }
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        onClick = goBack,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(30, 50, 70),
            contentColor = Color(255, 255, 255)
        )
    ) {
        Text("Tilbake", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}
