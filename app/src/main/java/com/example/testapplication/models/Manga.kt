package com.example.mangaviewer.models

import kotlinx.serialization.Serializable

@Serializable
data class MangaResponse(
    val data: List<Manga>
)

@Serializable
data class Manga(
    val title: String,
    var mal_id: Int,
    val images: MangaImage
) {

    val getTitle: String
        get() = title

    val getImage: String
        get() = images.jpg.image_url

    val getId: Int
        get() = mal_id
}

@Serializable
data class MangaImage(
    val jpg: MangaJpg
)

@Serializable
data class MangaJpg(
    val image_url: String
)