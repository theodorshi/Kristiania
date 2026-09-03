package com.example.jikanapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.jikanapp.data.database.animeideas.AnimeIdea

@Composable
fun AnimeIdeaItem(
    animeIdea: AnimeIdea
) {
    Column {
        Row {
            Text("Tittel: ", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(animeIdea.title, fontSize = 20.sp)
        }
        Row {
            Text("Kilde: ", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(animeIdea.source, fontSize = 20.sp)
        }
        Column {
            Text("Beskrivelse: ", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(animeIdea.description, fontSize = 16.sp)
        }
        Row {
            Text("Hovedkarakter: ", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            if (animeIdea.mainCharacter != null) {
                Text(animeIdea.mainCharacter, fontSize = 20.sp)
            }
        }
        Row {
            Text("Episoder: ", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(animeIdea.episodes.toString(), fontSize = 20.sp)
        }
    }
}