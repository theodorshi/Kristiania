package com.example.jikanapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jikanapp.data.database.animeideas.AnimeIdea



@Composable
fun AnimeIdeaListItem(
    animeIdea: AnimeIdea,
    alterAnime: () -> Unit
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
            .background(Color(141, 203, 234, 255))
            .border(
                2.dp,
                Color(10, 10, 10),
                RoundedCornerShape(8.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            animeIdea.title,
            modifier = Modifier.padding(8.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text("Id: " + animeIdea.id.toString())
        Text(
            "Beskrivelse:",
            fontWeight = FontWeight.Bold
        )
        Text(
            animeIdea.description,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        Button(
            onClick = alterAnime,
            modifier = Modifier
                .fillMaxWidth()
                .padding( 12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(30, 50, 70),
                contentColor = Color(255, 255, 255)
            )
        ) {
            Text("Rediger")
        }
    }
}