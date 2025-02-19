package com.example.mangaviewer.viewModels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangaviewer.models.MangaDetails
import com.example.mangaviewer.services.MangaService
import com.example.mangaviewer.storage.LocalStorageManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MangaViewModel(application: Application) : AndroidViewModel(application) {
    var mangaListResponse = mutableStateOf<List<MangaDetails>>(emptyList())
        private set
    var mangaRomanceResponse = mutableStateOf<List<MangaDetails>>(emptyList())
        private set
    var mangaSportResponse = mutableStateOf<List<MangaDetails>>(emptyList())
        private set
    var mangaDetailsResponse = mutableStateOf(MangaDetails())
        private set
    var mangaFavoritesResponse = mutableStateOf<List<MangaDetails>>(emptyList())
        private set
    private val localStorageManager: LocalStorageManager =
        LocalStorageManager(application.applicationContext)
    private var errorMessage = mutableStateOf("")
    private val _isLoading = MutableStateFlow(4)
    val favoriteMangaIds = localStorageManager.favoriteMangaIds

    val isLoading = _isLoading
        .onStart {
            getMangaList()
            getMangaRomance()
            getMangaSport()
            //getMangaFavorites()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            4
        )

    fun getMangaList() {
        viewModelScope.launch {
            val mangaService = MangaService.getInstance()
            try {
                val mangas = mangaService.getMangaList()
                mangaListResponse.value = mangas
                _isLoading.value -= 1
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "An error occured."
            }
        }
    }

    fun getMangaRomance() {
        viewModelScope.launch {
            val mangaService = MangaService.getInstance()
            try {
                val mangas = mangaService.getMangaGenre("22")
                mangaRomanceResponse.value = mangas
                _isLoading.value -= 1
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "An error occured."
            }
        }
    }

    fun getMangaSport() {
        viewModelScope.launch {
            val mangaService = MangaService.getInstance()
            try {
                val mangas = mangaService.getMangaGenre("30")
                mangaSportResponse.value = mangas
                _isLoading.value -= 1
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "An error occured."
            }
        }
    }

    fun getMangaFavorites() {
        viewModelScope.launch {
            val ids = localStorageManager.favoriteMangaIds.first()
            val mangaService = MangaService.getInstance()
            try {
                val favoriteMangas = ids.mapNotNull { id ->
                    mangaService.getMangaById(id.toIntOrNull() ?: return@mapNotNull null)
                }
                mangaFavoritesResponse.value = favoriteMangas
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "An error occurred."
            }
            _isLoading.value -= 1
        }
    }

    fun getMangaById(id: Int) {
        viewModelScope.launch {
            val mangaService = MangaService.getInstance()
            try {
                val mangas = mangaService.getMangaById(id)
                mangaDetailsResponse.value = mangas
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "An error occured."
            }
        }
    }

    fun addFavorite(mangaId: String) {
        viewModelScope.launch {
            localStorageManager.addMangaToFavorites(mangaId)
        }
    }

    fun removeFavorite(mangaId: String) {
        viewModelScope.launch {
            localStorageManager.removeMangaFromFavorites(mangaId)
        }
    }

}
