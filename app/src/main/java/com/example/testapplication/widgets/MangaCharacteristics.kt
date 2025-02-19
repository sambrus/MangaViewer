package com.example.mangaviewer.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mangaviewer.models.MangaDetails

@Composable
fun MangaCharacteristics(
    mangaDetails: MangaDetails
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        MangaCharacteristic(key = "Global Notes", value = "${mangaDetails.score} / 10")
        MangaCharacteristic(key = "Status", value = mangaDetails.status.uppercase())
        MangaCharacteristic(key = "Start Date", value = mangaDetails.getStartingDate)
        MangaCharacteristic(key = "End Date", value = mangaDetails.getEndingDate)
        MangaCharacteristic(key = "Author", value = mangaDetails.getAuthor)
    }
}

@Composable
fun MangaCharacteristic(key: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = key,
            color = Color.Gray,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }
}
