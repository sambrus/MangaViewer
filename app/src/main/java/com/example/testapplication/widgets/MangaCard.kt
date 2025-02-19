package com.example.mangaviewer.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mangaviewer.models.MangaDetails

@Composable
fun MangaCard(
    manga: MangaDetails,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxHeight()
            .width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .size(100.dp, 150.dp)
                .clickable { onClick() },
            shape = RoundedCornerShape(12.dp),
        ) {
            MangaImage(url = manga.getImage)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = manga.getTitle,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}
