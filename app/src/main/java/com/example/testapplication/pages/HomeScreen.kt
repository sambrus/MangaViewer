package com.example.mangaviewer.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mangaviewer.viewModels.MangaViewModel
import com.example.mangaviewer.widgets.ImageWithChildren
import com.example.mangaviewer.widgets.MangaRowWrapper

@Composable
fun HomeScreen(
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val viewModel: MangaViewModel = viewModel()

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getMangaFavorites()
    }
    Box(
        modifier = Modifier.background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            ImageWithChildren(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((LocalConfiguration.current.screenHeightDp * 0.4).dp)
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = (LocalConfiguration.current.screenHeightDp * 0.1).dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Manga Viewer",
                        color = Color.White,
                        fontSize = 36.sp,
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "A small app project to visualize and pin your mangas!",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(4.dp),
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                }
            }
            if (isLoading <= 0) {
                if (viewModel.mangaFavoritesResponse.value.isNotEmpty()) {
                    MangaRowWrapper(
                        mangas = viewModel.mangaFavoritesResponse.value,
                        label = "Favorites Mangas",
                        navController = navController
                    )
                }
                MangaRowWrapper(
                    mangas = viewModel.mangaListResponse.value,
                    label = "Suggested Mangas",
                    navController = navController
                )
                MangaRowWrapper(
                    mangas = viewModel.mangaSportResponse.value,
                    label = "Sport Mangas",
                    navController = navController
                )
                MangaRowWrapper(
                    mangas = viewModel.mangaRomanceResponse.value,
                    label = "Romance Mangas",
                    navController = navController
                )
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.Green
                )
            }
        }
    }
}
