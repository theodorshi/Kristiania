package com.example.jikanapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jikanapp.data.database.animeideas.AnimeIdeaDbRepository
import com.example.jikanapp.data.database.animefavorites.AnimeFavoriteDbRepository
import com.example.jikanapp.navigation.AppNavigation
import com.example.jikanapp.screens.animedetails.AnimeDetailsViewModel
import com.example.jikanapp.screens.animefavorites.AnimeFavoritesViewModel
import com.example.jikanapp.screens.animeideas.AnimeIdeasViewModel
import com.example.jikanapp.screens.animes.AnimesViewModel
import com.example.jikanapp.screens.animesearch.AnimeSearchViewModel
import com.example.jikanapp.screens.animeupdate.AnimeUpdateViewModel
import com.example.jikanapp.ui.theme.JikanAppTheme

class MainActivity : ComponentActivity() {
    private val _animeViewModel : AnimesViewModel by viewModels()
    private val _animeSearchViewModel : AnimeSearchViewModel by viewModels()
    private val _animeIdeasViewModel : AnimeIdeasViewModel by viewModels()
    private val _animeDetailsViewModel : AnimeDetailsViewModel by viewModels()
    private val _animeUpdateViewModel : AnimeUpdateViewModel by viewModels()
    private val _animeFavoritesViewModel : AnimeFavoritesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AnimeIdeaDbRepository.initializeDatabase(applicationContext)
        AnimeFavoriteDbRepository.initializeDatabase(applicationContext)

        enableEdgeToEdge()
        setContent {
            JikanAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column (modifier = Modifier.padding(innerPadding)){
                        AppNavigation(_animeViewModel
                            , _animeIdeasViewModel,
                            _animeSearchViewModel,
                            _animeDetailsViewModel,
                            _animeUpdateViewModel,
                            _animeFavoritesViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JikanAppTheme {
        Greeting("Android")
    }
}