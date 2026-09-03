package com.example.jikanapp.screens.animesearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jikanapp.components.AnimeItem
import com.example.jikanapp.components.AnimeListItem
import com.example.jikanapp.components.AnimeSearchItem
import com.example.jikanapp.navigation.NavigationRoutes

@Composable
fun AnimeSearchScreen(animeSearchViewModel: AnimeSearchViewModel) {
    val anime = animeSearchViewModel.anime.collectAsState()
    val animesByScore = animeSearchViewModel.animesByScore.collectAsState()

    var id by remember {
        mutableStateOf("")
    }
    var minScore by remember {
        mutableStateOf("")
    }

    var statusMessageId by remember {
        mutableStateOf("")
    }
    var statusMessageScore by remember {
        mutableStateOf("")
    }
    var showHideButton by remember {
        mutableStateOf(true)
    }


    //Resetter søk ved innlasting av siden
    LaunchedEffect(Unit) {
        animeSearchViewModel.clearAnime()
        animeSearchViewModel.clearAnimesByScore()
        statusMessageId = ""
        statusMessageScore = ""
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            "Søk etter anime",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Finn anime basert på id", fontSize = 16.sp)
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = id,
                    onValueChange = { id = it },
                    label = { Text("Id") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        val idParsed = id.toIntOrNull()
                        if (idParsed != null) {
                            animeSearchViewModel.setAnimeById(idParsed)
                            if (anime.value == null) {
                                statusMessageId = "Ingen anime med denne id finnes"
                                showHideButton = false
                            }
                        }
                        showHideButton = true
                    }
                ) {
                    Text("Søk", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                anime.value?.let {
                    AnimeSearchItem(
                        it
                    )
                    // Knapp for å skjule animen
                    if (showHideButton) {
                    Button(
                        onClick = {
                            animeSearchViewModel.clearAnime()
                            showHideButton = false
                            statusMessageId = ""
                        }
                    ) {
                        Text("Skjul")
                    }
                    }
                } ?: Text(statusMessageId)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Finn alle animer basert på minimum score", fontSize = 16.sp)

                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = minScore,
                    onValueChange = { minScore = it },
                    label = { Text("Score (1-10)") }
                )
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        val parsedScore = minScore.toDoubleOrNull()
                        if (parsedScore != null) {
                            if (parsedScore > 10) {
                                statusMessageScore = "Skriv et tall mellom 1-10"
                                animeSearchViewModel.clearAnimesByScore()
                            } else {
                                statusMessageScore = ""
                                animeSearchViewModel.setAnimesByScore(parsedScore)
                            }
                        }
                    }
                ) {
                    Text("Søk på score", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                if (animesByScore.value.isNotEmpty()) {
                    LazyColumn {
                        items(animesByScore.value) { anime ->
                            AnimeSearchItem(anime)
                        }
                    }
                } else {
                    Text(statusMessageScore)
                }

            }
        }
    }
}