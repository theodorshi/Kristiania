package com.example.jikanapp.screens.animeideas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jikanapp.components.AnimeIdeaListItem
import com.example.jikanapp.data.database.animeideas.AnimeIdea
import com.example.jikanapp.navigation.NavigationRoutes
import kotlinx.coroutines.delay
import org.intellij.lang.annotations.JdkConstants

@Composable
fun AnimeIdeasScreen(animeIdeasViewModel: AnimeIdeasViewModel, navController: NavController) {
    val animes = animeIdeasViewModel.animes.collectAsState()
    val animeBySearchResult = animeIdeasViewModel.animeBySearchResult.collectAsState()

    LaunchedEffect(Unit) {
        animeIdeasViewModel.setAnimes()
    }


    //Er felt synlig?
    var addAnimeIsVisable by remember { mutableStateOf(false) }
    var searchAnimeIsVisable by remember { mutableStateOf(false) }
    var addAnimeStatusMessageVisable by remember { mutableStateOf(false) }
    var searchStatusMessageVisable by remember { mutableStateOf(false) }

    //Statusmelding
    var statusMessage by remember { mutableStateOf("") }
    val dbStatusMessage = animeIdeasViewModel.dbStatusMessage
    var searchStatusMessage by remember { mutableStateOf("") }


    LaunchedEffect(statusMessage, dbStatusMessage, searchStatusMessage) {
        if (statusMessage == "Fyll ut alle påkrevde felter") {
            delay(3000)
            statusMessage = ""
            addAnimeStatusMessageVisable = false
        }
        if (dbStatusMessage == "Lagt til i databasen" ||
            dbStatusMessage == "Kunne ikke legge til i databasen"
        ) {
            delay(3000)
            animeIdeasViewModel.resetDbStatusMessage()
            addAnimeStatusMessageVisable = false

        }
        if (searchStatusMessage == "Finner ikke i databasen" ||
            searchStatusMessage == "Skriv inn tekst for å søke"
        ) {
            delay(3000)
            searchStatusMessageVisable = false
        }
    }

    var newTitle by remember {
        mutableStateOf("")
    }
    var newSource by remember {
        mutableStateOf("")
    }
    var newNumberOfEpisodes by remember {
        mutableStateOf("")
    }
    var newDescription by remember {
        mutableStateOf("")
    }
    var newMainCharacter by remember {
        mutableStateOf("")
    }

    var searchText by remember { mutableStateOf("") }

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            "Mine animeideer",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        //Skul og vis knapp
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Legge til ny anime
            Button(
                onClick = {
                    addAnimeIsVisable = !addAnimeIsVisable
                    searchAnimeIsVisable = false
                },
                modifier = Modifier
                    .padding(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(30, 50, 70),
                    contentColor = Color(255, 255, 255)
                )
            ) {
                Text(
                    if (addAnimeIsVisable) {
                        "Skjul legg til"
                    } else {
                        "Legge til ny ide?"
                    }
                )
            }
            //Søke etter anime
            Button(
                onClick = {
                    searchAnimeIsVisable = !searchAnimeIsVisable
                    addAnimeIsVisable = false
                    searchText = ""
                    animeIdeasViewModel.resetAnimeSearchByTitle()
                },
                modifier = Modifier
                    .padding(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(30, 50, 70),
                    contentColor = Color(255, 255, 255)
                )
            ) {
                Text(
                    if (searchAnimeIsVisable) {
                        "Skjul søk etter ide"
                    } else {
                        "Søk etter ide"
                    }
                )
            }
        }

        //legg til ny anime felt
        if (addAnimeIsVisable) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = newTitle,
                onValueChange = { newTitle = it },
                label = { Text("Tittel") }
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = newSource,
                onValueChange = { newSource = it },
                label = { Text("Kilde (Manga, Anime osv)") }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = newDescription,
                onValueChange = { newDescription = it },
                label = { Text("Beskrivelse") }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = newMainCharacter,
                onValueChange = { newMainCharacter = it },
                label = { Text("Hovedperson (Valgfri)") }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = newNumberOfEpisodes,
                onValueChange = { newNumberOfEpisodes = it },
                label = { Text("Antall episoder (Valgfri)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )


            Button(
                onClick = {
                    if (
                        newTitle.isNotBlank() &&
                        newSource.isNotBlank() &&
                        newDescription.isNotBlank()
                    ) {
                        val newAnimeIdeaIdea = AnimeIdea(
                            title = newTitle,
                            source = newSource,
                            episodes = newNumberOfEpisodes.toIntOrNull() ?: 0,
                            description = newDescription,
                            mainCharacter = newMainCharacter
                        )

                        animeIdeasViewModel.insertAnime(newAnimeIdeaIdea)
                        addAnimeStatusMessageVisable = true

                        newTitle = ""
                        newSource = ""
                        newNumberOfEpisodes = ""
                        newDescription = ""
                        newMainCharacter = ""

                    } else {
                        statusMessage = "Fyll ut alle påkrevde felter"
                        addAnimeStatusMessageVisable = true

                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(76, 175, 80, 255),
                    contentColor = Color(255, 255, 255)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Legg til Anime i databasen", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            //Statusmelding fra database og om felter er fylt ut
            if (addAnimeStatusMessageVisable) {
                Text(
                    statusMessage + dbStatusMessage,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )
            }
        }

        //Søk på anime felt
        if (searchAnimeIsVisable) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Søk etter ide med tittel") }

            )

            Button(
                onClick = {
                    if (searchText.isBlank()) {
                        searchStatusMessage = "Skriv inn tekst for å søke"
                        searchStatusMessageVisable = true
                    } else {
                        animeIdeasViewModel.setAnimeByTitle(searchText)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(30, 50, 70),
                    contentColor = Color(255, 255, 255)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Søk", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            if (searchStatusMessageVisable) {
                Text(searchStatusMessage)
            }

            val searchResult = animeBySearchResult.value
            if (searchResult.isNotEmpty()) {
                LazyColumn {
                    items(searchResult) { anime ->
                        AnimeIdeaListItem(
                            animeIdea = anime,
                            alterAnime = {
                                navController.navigate(
                                    NavigationRoutes.AnimeUpdateRoute(
                                        anime.id
                                    )
                                )
                            }
                        )
                    }
                }
            }

        }

        if (animes.value != emptyList<AnimeIdea>()) {
            Text(
                "Animeideer i databasen",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            LazyColumn(
            ) {
                items(animes.value) { anime ->
                    AnimeIdeaListItem(
                        anime,
                        alterAnime = {
                            navController.navigate(
                                NavigationRoutes.AnimeUpdateRoute(
                                    anime.id
                                )
                            )
                        }
                    )
                }
            }
        } else {
            Text(
                "Ingen animeideer er lagt til i databasen",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

