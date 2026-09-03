package com.example.jikanapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.jikanapp.data.database.animefavorites.AnimeFavorite


@Composable
fun AnimeFavoriteItem(
    animeFavorite: AnimeFavorite,
    showAnime: () -> Unit,
    removeFavorite: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                clip = true
            )
            .background(Color(118, 42, 245, 255))
            .border(
                2.dp,
                Color(10, 10, 10),
                RoundedCornerShape(8.dp)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            animeFavorite.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color.White
        )
        AsyncImage(
            model = animeFavorite.images,
            contentDescription = "Bilde av ${animeFavorite.title}"
        )
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
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
                onClick = removeFavorite,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(122, 0, 0, 255),
                    contentColor = Color(255, 255, 255)
                )
            ) {
                Text("Fjern fra favoritter")
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Ikon av et hjerte",
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }
    }
}