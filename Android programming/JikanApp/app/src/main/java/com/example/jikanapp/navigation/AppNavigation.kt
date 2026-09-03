package com.example.jikanapp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.jikanapp.components.AnimeItem

import com.example.jikanapp.screens.animedetails.AnimeDetailsScreen
import com.example.jikanapp.screens.animedetails.AnimeDetailsViewModel
import com.example.jikanapp.screens.animefavorites.AnimeFavoritesScreen
import com.example.jikanapp.screens.animefavorites.AnimeFavoritesViewModel
import com.example.jikanapp.screens.animeideas.AnimeIdeasScreen
import com.example.jikanapp.screens.animeideas.AnimeIdeasViewModel
import com.example.jikanapp.screens.animes.AnimesScreen
import com.example.jikanapp.screens.animes.AnimesViewModel
import com.example.jikanapp.screens.animesearch.AnimeSearchScreen
import com.example.jikanapp.screens.animesearch.AnimeSearchViewModel
import com.example.jikanapp.screens.animeupdate.AnimeUpdateScreen
import com.example.jikanapp.screens.animeupdate.AnimeUpdateViewModel

@Composable
fun AppNavigation(
    animesViewModel: AnimesViewModel,
    animeIdeasViewModel: AnimeIdeasViewModel,
    animeSearchViewModel: AnimeSearchViewModel,
    animeDetailsViewModel: AnimeDetailsViewModel,
    animeUpdateViewModel: AnimeUpdateViewModel,
    animeFavoritesViewModel: AnimeFavoritesViewModel
) {
    val navController = rememberNavController()
    var activeItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    val animeTheme = NavigationBarItemDefaults.colors(
        indicatorColor = Color.Transparent,
        selectedIconColor = Color(200,200,200),
        selectedTextColor = Color(200,200,200),
        unselectedIconColor = Color(130,150,170),
        unselectedTextColor = Color(140,170,190)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color(30,50,70)
            ) {
                NavigationBarItem(
                    selected = activeItem == 0,
                    onClick = {
                        activeItem = 0
                        navController.navigate(NavigationRoutes.AnimesRoute)
                    },
                    label = { Text("Animer") },
                    icon = {
                        if (activeItem == 0) {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = "Hjem-ikon"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Home,
                                contentDescription = "Hjem-ikon"
                            )
                        }
                    },
                    colors = animeTheme
                )
                NavigationBarItem(
                    selected = activeItem == 1,
                    onClick = {
                        activeItem = 1
                        navController.navigate(NavigationRoutes.AnimeSearchRoute)
                    },
                    label = { Text("Søk") },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    colors = animeTheme
                )
                NavigationBarItem(
                    selected = activeItem == 2,
                    onClick = {
                        activeItem = 2
                        navController.navigate(NavigationRoutes.AnimeIdeasRoute)
                    },
                    label = { Text("Animer") },
                    icon = {
                        if (activeItem == 2) {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = "Legg til ikon"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.AddCircle,
                                contentDescription = "Legg til ikon"
                            )
                        }
                    },
                    colors = animeTheme
                )
                NavigationBarItem(
                    selected = activeItem == 3,
                    onClick = {
                        activeItem = 3
                        navController.navigate(NavigationRoutes.AnimeFavoritesRoute)
                    },
                    label = { Text("Favoritter") },
                    icon = {
                        if (activeItem == 3) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Favoritt-ikon"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favoritt-ikon"
                            )
                        }
                    },
                    colors = animeTheme
                )
            }
        }
    ) { innerPadding ->
        Column (modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = NavigationRoutes.AnimesRoute
            ) {
                composable<NavigationRoutes.AnimesRoute> {
                    AnimesScreen(animesViewModel, navController)
                }
                composable<NavigationRoutes.AnimeSearchRoute> {
                    AnimeSearchScreen(animeSearchViewModel)
                }
                composable<NavigationRoutes.AnimeIdeasRoute> {
                    AnimeIdeasScreen(animeIdeasViewModel, navController)
                }
                composable<NavigationRoutes.AnimeFavoritesRoute> {
                    AnimeFavoritesScreen(animeFavoritesViewModel, navController)
                }
                composable<NavigationRoutes.AnimeDetailsRoute> { backStackEntry ->
                    val args = backStackEntry.toRoute<NavigationRoutes.AnimeDetailsRoute>()
                    AnimeDetailsScreen(
                        animeDetailsViewModel,
                        navController,
                        args.animeId
                    )
                }
                composable<NavigationRoutes.AnimeUpdateRoute> { backStackEntry ->
                    val args = backStackEntry.toRoute<NavigationRoutes.AnimeUpdateRoute>()
                    AnimeUpdateScreen(
                        animeUpdateViewModel,
                        navController,
                        args.animeId
                    )
                }
            }
        }

    }
}

