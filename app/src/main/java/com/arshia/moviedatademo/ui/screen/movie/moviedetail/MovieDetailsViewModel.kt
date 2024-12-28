package com.arshia.moviedatademo.ui.screen.movie.moviedetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.moviedatademo.data.model.MovieList
import com.arshia.moviedatademo.data.model.favoritemovie.FavoriteMovie
import com.arshia.moviedatademo.data.model.moviedetails.MovieDetails
import com.arshia.moviedatademo.data.model.persondetails.Credits
import com.arshia.moviedatademo.data.repository.remote.DataRepository
import com.arshia.moviedatademo.data.repository.room.DatabaseRepository
import com.arshia.moviedatademo.utils.network.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: DataRepository,
    private val databaseRepository: DatabaseRepository
) :
    ViewModel() {

    val movieDetails: MutableState<DataResult<MovieDetails>?> = mutableStateOf(null)
    val recommendedMovieList: MutableState<DataResult<MovieList>?> = mutableStateOf(null)
    val movieCredits: MutableState<DataResult<Credits>?> = mutableStateOf(null)

    val movieInPlayList: MutableState<FavoriteMovie?> = mutableStateOf(null)

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            repository.movieDetails(movieId).onEach {
                movieDetails.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchRecommendedMovieList(movieId: Int) {
        viewModelScope.launch {
            repository.recommendedMovieList(movieId).onEach {
                recommendedMovieList.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchMovieCredits(movieId: Int) {
        viewModelScope.launch {
            repository.movieCredits(movieId).onEach {
                movieCredits.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun addToPlayList(movie: MovieDetails) {
        viewModelScope.launch {
            databaseRepository.insertMovie(
                FavoriteMovie(
                    movieId = movie.id,
                    posterPath = movie.posterPath
                )
            )
        }
    }

    fun removeFromPlaylist(movie: FavoriteMovie) {
        viewModelScope.launch {
            databaseRepository.deleteMovie(movie)
        }
    }

    fun findMovieById(movieId: Int) {
        viewModelScope.launch {
            databaseRepository.findMovieById(movieId).onEach {
                movieInPlayList.value = it
            }.launchIn(viewModelScope)
        }
    }
}