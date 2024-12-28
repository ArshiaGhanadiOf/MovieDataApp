package com.arshia.moviedatademo.ui.screen.movie.searchcontent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.arshia.moviedatademo.data.repository.remote.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchContentViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {
    var searchedKey = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    var searchedMovieList = searchedKey.flatMapLatest { query ->
        repository.searchedMoviePagingSource(query)
    }.cachedIn(viewModelScope)
}