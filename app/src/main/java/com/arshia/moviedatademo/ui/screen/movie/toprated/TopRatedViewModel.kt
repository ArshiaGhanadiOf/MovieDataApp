package com.arshia.moviedatademo.ui.screens.movie.toprated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.arshia.moviedatademo.data.base.BaseGenre
import com.arshia.moviedatademo.data.repository.remote.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class TopRatedViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {
    var selectedGenre = MutableStateFlow(BaseGenre.BASE_DEFAULT_GENRE)

    @OptIn(ExperimentalCoroutinesApi::class)
    var topRatedMovieList = selectedGenre.flatMapLatest { genre ->
        repository.topRatedMoviePagingSource(genre.id)
    }.cachedIn(viewModelScope)
}