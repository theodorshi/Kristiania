package com.example.jikanapp.screens.animeupdate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jikanapp.components.AnimeIdeaItem
import kotlinx.coroutines.delay

@Composable
fun AnimeUpdateScreen(
    animeUpdateViewModel: AnimeUpdateViewModel,
    navController: NavController,
    animeId: Int,
) {
    val anime = animeUpdateViewModel.anime.collectAsState()

    LaunchedEffect(Unit) {
        animeUpdateViewModel.loadAnime(animeId)
    }

    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Rediger",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center)
        anime.value?.let { loadedAnime ->

            var title by remember(loadedAnime.id) {
                mutableStateOf(loadedAnime.title)
            }

            var source by remember(loadedAnime.id) {
                mutableStateOf(loadedAnime.source)
            }

            var episodes by remember(loadedAnime.id) {
                mutableStateOf(loadedAnime.episodes)
            }

            var description by remember(loadedAnime.id) {
                mutableStateOf(loadedAnime.description)
            }

            var mainCharacter by remember(loadedAnime.id) {
                mutableStateOf(loadedAnime.mainCharacter)
            }

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Column (modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.Start

                    ){
                    AnimeIdeaItem(loadedAnime)
                    }


                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = source,
                    onValueChange = { source = it },
                    label = { Text("Kilde") }
                )


                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Beskrivelse") }
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = mainCharacter.toString(),
                    onValueChange = { mainCharacter = it },
                    label = { Text("Hovedkarakter (Valgfri)") }
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = episodes.toString(),
                    onValueChange = { episodes = it.toIntOrNull() ?: 0 },
                    label = { Text("Episoder (Valgfri)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                //Statusmelding
                var statusMessage by remember { mutableStateOf("") }
                var statusColor by remember { mutableStateOf(Color(0,0,0)) }

                Text( text = statusMessage,
                    color = statusColor)

                LaunchedEffect(statusMessage) {
                    if (statusMessage == "Animen ble slettet"){
                        delay(3000)
                    navController.popBackStack()}
                }

                //Sender nytt objekt inn til update
                val updatedAnime = loadedAnime.copy(
                    title = title,
                    source = source,
                    episodes = episodes,
                    description = description,
                    mainCharacter = mainCharacter
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ){
                    Button(
                        onClick = {
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(30, 50, 70),
                            contentColor = Color(255, 255, 255)),
                        modifier = Modifier
                            .width(120.dp)
                            .height(50.dp)
                    ) {
                        Text(
                            "Tilbake",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        onClick = {
                            if (title.isNotBlank() && source.isNotBlank() && description.isNotBlank()) {


                                animeUpdateViewModel.updateAnime(
                                    updatedAnime
                                )
                                statusMessage = "Animen ble lagret!"
                                statusColor = Color(76, 175, 80, 255)
                            } else {
                                statusMessage = "Fyll ut alle påkrevde felter"
                                statusColor = Color(122, 0, 0, 255)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(76, 175, 80, 255),
                            contentColor = Color(255, 255, 255)
                        ),
                        modifier = Modifier
                            .width(120.dp)
                            .height(50.dp)
                    ) {
                        Text(
                            "Lagre",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = {
                            animeUpdateViewModel.deleteAnime(loadedAnime)
                            statusMessage = "Animen ble slettet"
                            statusColor = Color(122, 0, 0, 255)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(180, 60, 60),
                            contentColor = Color(255, 255, 255)),
                        modifier = Modifier
                            .width(120.dp)
                            .height(50.dp)
                    ) {
                        Text(
                            "Slett",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}