package com.example.jikanapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import coil.compose.AsyncImage
import com.example.jikanapp.data.api.Anime
import com.example.jikanapp.data.database.animefavorites.AnimeFavorite


@Composable
fun AnimeListItem(
    animeAPI: Anime,
    showAnime: () -> Unit,
    addFavorite: () -> Unit,
) {
    var isClicked by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                clip = true
            )
            .background(Color(141, 203, 234, 255))
            .border(
                2.dp,
                Color(10, 10, 10),
                RoundedCornerShape(8.dp)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            animeAPI.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        AsyncImage(
            model = animeAPI.images.jpg.image,
            contentDescription = "Bilde av ${animeAPI.title}",
            modifier = Modifier
                .fillMaxWidth(1f)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = showAnime,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(30, 50, 70),
                    contentColor = Color(255, 255, 255)
                )
            ) {
                Text("Se mer")
            }
            Button(
                onClick = {
                    addFavorite()
                    isClicked = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(103, 58, 183, 255),
                    contentColor = Color(255, 255, 255)
                )
            ) {
                if (isClicked) {
                    Text("Lagt til i favoritter")
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Ikon av et hjerte",
                        modifier = Modifier
                            .size(20.dp)
                    )
                } else {
                    Text("Legg til i favoritter")
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Ikon av et hjerte",
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            }
        }
    }
}

