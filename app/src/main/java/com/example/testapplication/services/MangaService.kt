package com.example.mangaviewer.services

import com.example.mangaviewer.models.ListMangaDetailsResponse
import com.example.mangaviewer.models.MangaDetails
import com.example.mangaviewer.models.MangaDetailsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

class MangaService {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun getMangaList(maxRetries: Int = 3): List<MangaDetails> {
        var attempt = 0
        while (attempt < maxRetries) {
            try {
                val response: ListMangaDetailsResponse =
                    client.get("https://api.jikan.moe/v4/manga?start_date=2015-01-01&sfw=false&limit=10")
                        .body()
                //val response: ListMangaDetailsResponse = client.get("https://api.jikan.moe/v4/manga?q=the%20promised").body()
                return response.data
            } catch (e: Exception) {
                attempt++
                println("Erreur get manga list (tentative $attempt): ${e.message}")
                if (attempt >= maxRetries) {
                    return emptyList()
                }
                delay(1000)
            }
        }
        return emptyList()
    }

    suspend fun getMangaGenre(genre: String, maxRetries: Int = 3): List<MangaDetails> {
        var attempt = 0
        while (attempt < maxRetries) {
            try {
                val response: ListMangaDetailsResponse =
                    client.get("https://api.jikan.moe/v4/manga?start_date=2020-01-01&genres=${genre}&sfw=true&limit=10")
                        .body()
                return response.data
            } catch (e: Exception) {
                attempt++
                println("Erreur get manga genre (tentative $attempt): ${e.message}")
                if (attempt >= maxRetries) {
                    return emptyList()
                }
                delay(1000)
            }
        }
        return emptyList()
    }

    suspend fun getMangaById(id: Int, maxRetries: Int = 3): MangaDetails {
        var attempt = 0
        while (attempt < maxRetries) {
            try {
                val response: MangaDetailsResponse =
                    client.get("https://api.jikan.moe/v4/manga/${id}").body()
                return response.data
            } catch (e: Exception) {
                attempt++
                println("Erreur get manga id (tentative $attempt): ${e.message}")
                if (attempt >= maxRetries) {
                    return MangaDetails()
                }
                delay(1000)
            }
        }
        return MangaDetails()
    }

    companion object {
        @Volatile
        private var instance: MangaService? = null
        fun getInstance(): MangaService {
            return instance ?: synchronized(this) {
                instance ?: MangaService().also { instance = it }
            }
        }
    }
}
