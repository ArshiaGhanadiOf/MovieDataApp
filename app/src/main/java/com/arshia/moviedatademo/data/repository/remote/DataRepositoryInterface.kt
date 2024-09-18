package com.arshia.moviedatademo.data.repository.remote

import androidx.paging.PagingData
import com.arshia.moviedatademo.data.model.Genres
import com.arshia.moviedatademo.data.model.ItemMovie
import com.arshia.moviedatademo.data.model.MovieList
import com.arshia.moviedatademo.data.model.persondetails.Credits
import com.arshia.moviedatademo.data.model.moviedetails.MovieDetails
import com.arshia.moviedatademo.data.model.persondetails.PersonCredits
import com.arshia.moviedatademo.data.model.persondetails.PersonDetails
import com.arshia.moviedatademo.utils.network.DataResult
import kotlinx.coroutines.flow.Flow

interface DataRepositoryInterface {
    fun nowPlayingMoviePagingSource(genreId: Int?): Flow<PagingData<ItemMovie>>
    fun popularMoviePagingSource(genreId: Int?): Flow<PagingData<ItemMovie>>
    fun topRatedMoviePagingSource(genreId: Int?): Flow<PagingData<ItemMovie>>
    fun upcomingMoviePagingSource(genreId: Int?): Flow<PagingData<ItemMovie>>
    fun searchedMoviePagingSource(searchedKey: String): Flow<PagingData<ItemMovie>>
    suspend fun genreMovieList(): Flow<DataResult<Genres>>
    suspend fun movieDetails(movieId: Int): Flow<DataResult<MovieDetails>>
    suspend fun recommendedMovieList(movieId: Int): Flow<DataResult<MovieList>>
    suspend fun movieCredits(movieId: Int): Flow<DataResult<Credits>>
    suspend fun personDetails(personId: Int): Flow<DataResult<PersonDetails>>
    suspend fun personMovieCredits(personId: Int): Flow<DataResult<PersonCredits>>
}