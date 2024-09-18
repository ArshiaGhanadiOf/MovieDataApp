package com.arshia.moviedatademo.ui.screens.movie.discover

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.moviedatademo.data.model.Genres
import com.arshia.moviedatademo.data.repository.remote.DataRepository
import com.arshia.moviedatademo.utils.network.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDiscoverViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {
    val genreList: MutableState<DataResult<Genres>?> = mutableStateOf(null)
    fun fetchGenreList() {
        viewModelScope.launch {
            repository.genreMovieList().onEach {
                genreList.value = it
            }.launchIn(viewModelScope)
        }
    }
}