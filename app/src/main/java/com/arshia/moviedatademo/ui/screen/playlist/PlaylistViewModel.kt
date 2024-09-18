package com.arshia.moviedatademo.ui.screens.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.moviedatademo.data.model.favoritemovie.FavoriteMovie
import com.arshia.moviedatademo.data.repository.room.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(private val databaseRepository: DatabaseRepository) : ViewModel() {
    private val _playlist = MutableStateFlow(emptyList<FavoriteMovie>())
    val playlist = _playlist.asStateFlow()


    fun getPlaylist() {
        viewModelScope.launch {
            databaseRepository.getAllFavoriteMovie().collectLatest {
                _playlist.tryEmit(it)
            }
        }
    }
}