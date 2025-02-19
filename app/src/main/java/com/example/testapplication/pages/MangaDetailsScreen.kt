package com.example.mangaviewer.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mangaviewer.viewModels.MangaViewModel
import com.example.mangaviewer.widgets.ImageWithChildren
import com.example.mangaviewer.widgets.MangaCharacteristics
import com.example.mangaviewer.widgets.MangaImage

@Composable
fun MangaDetailsScreen(
    mangaId: Int
) {
    val viewModel: MangaViewModel = viewModel()

    val favoriteIds by viewModel.favoriteMangaIds.collectAsState(initial = emptyList())
    val isFavorite =
        remember(favoriteIds) { mutableStateOf(favoriteIds.contains(mangaId.toString())) }
    // TODO voir pour adapter ça avec le .onStart du viewModel mais c'est surement mieux de gérer avec un autre viewModel
    LaunchedEffect(Unit) {
        viewModel.getMangaById(mangaId)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            ImageWithChildren(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((LocalConfiguration.current.screenHeightDp * 0.5).dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = (LocalConfiguration.current.screenHeightDp * 0.02).dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier
                            .size(100.dp, 150.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        MangaImage(url = viewModel.mangaDetailsResponse.value.getImage)
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = viewModel.mangaDetailsResponse.value.getTitle,
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            color = Color.White,
                            letterSpacing = 0.5.sp
                        ),
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Chapters : ${viewModel.mangaDetailsResponse.value.chapters ?: "-"}, Volumes : ${viewModel.mangaDetailsResponse.value.volumes ?: "-"}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Synopsis",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = {
                        if (!isFavorite.value)
                            viewModel.addFavorite(mangaId.toString())
                        else
                            viewModel.removeFavorite(mangaId.toString())
                        isFavorite.value = !isFavorite.value
                    }
                ) {
                    Icon(
                        imageVector = if (isFavorite.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favori",
                        tint = if (isFavorite.value) Color.Red else Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            Text(
                text = viewModel.mangaDetailsResponse.value.synopsis ?: "Unknown history.",
                maxLines = 5,
                fontSize = 10.sp,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            MangaCharacteristics(mangaDetails = viewModel.mangaDetailsResponse.value)
        }
    }
}
