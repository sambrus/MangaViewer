package com.example.mangaviewer.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mangaviewer.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LocalStorageManager(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val FAVORITES_KEY = stringPreferencesKey("favorites_manga_ids")
    }

    val favoriteMangaIds: Flow<List<String>> = dataStore.data
        .map { preferences ->
            preferences[FAVORITES_KEY]?.split(",")?.filter { it.isNotEmpty() } ?: emptyList()
        }

    suspend fun addMangaToFavorites(mangaId: String) {
        dataStore.edit { preferences ->
            val currentIds =
                preferences[FAVORITES_KEY]?.split(",")?.toMutableList() ?: mutableListOf()
            if (!currentIds.contains(mangaId)) {
                currentIds.add(mangaId)
                preferences[FAVORITES_KEY] = currentIds.joinToString(",")
            }
        }
    }

    suspend fun removeMangaFromFavorites(mangaId: String) {
        dataStore.edit { preferences ->
            val currentIds =
                preferences[FAVORITES_KEY]?.split(",")?.toMutableList() ?: mutableListOf()
            currentIds.remove(mangaId)
            preferences[FAVORITES_KEY] = currentIds.joinToString(",")
        }
    }

    suspend fun isMangaFavorite(mangaId: String): Boolean {
        return favoriteMangaIds.first().contains(mangaId)
    }
}
