package com.example.mangaviewer.models

import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Serializable
data class MangaDetailsResponse(
    val data: MangaDetails
)

@Serializable
data class ListMangaDetailsResponse(
    val data: List<MangaDetails>
)

@Serializable
data class MangaDetails(
    val mal_id: Int = 0,
    val title: String = "",
    val images: MangaImage = MangaImage(MangaJpg("")),
    val synopsis: String? = null,
    val status: String = "",
    val chapters: Int? = null,
    val volumes: Int? = null,
    val authors: List<MangaAuthor> = emptyList(),
    val score: Float? = null,
    val published: MangaDates = MangaDates()
) {
    val getTitle: String
        get() = title

    val getImage: String
        get() = images.jpg.image_url

    val getStartingDate: String
        get() = if (published.from == null)
            "Never"
        else
            formatDate(published.from)

    val getEndingDate: String
        get() = if (published.to == null)
            "Ongoing"
        else
            formatDate(published.to)

    val getId: Int
        get() = mal_id

    private fun formatDate(isoDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH)
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")

            val outputFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH)
            val date = inputFormat.parse(isoDate)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            "Invalid date"
        }

    }

    val getAuthor: String
        get() = if (authors.isEmpty())
            "Unknown"
        else
            authors[0].name
}

@Serializable
data class MangaDates(
    val from: String? = null,
    val to: String? = null,
)

@Serializable
data class MangaAuthor(
    val name: String
)