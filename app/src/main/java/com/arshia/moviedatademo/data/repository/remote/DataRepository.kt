package com.arshia.moviedatademo.data.repository.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arshia.moviedatademo.data.datasource.remote.DataApiService
import com.arshia.moviedatademo.data.datasource.remote.paging.NowPlayingMoviePagingSource
import com.arshia.moviedatademo.data.datasource.remote.paging.PopularMoviePagingSource
import com.arshia.moviedatademo.data.datasource.remote.paging.SearchedMoviePagingSource
import com.arshia.moviedatademo.data.datasource.remote.paging.TopRatedMoviePagingSource
import com.arshia.moviedatademo.data.datasource.remote.paging.UpcomingMoviePagingSource
import com.arshia.moviedatademo.data.model.Genres
import com.arshia.moviedatademo.data.model.ItemMovie
import com.arshia.moviedatademo.data.model.MovieList
import com.arshia.moviedatademo.data.model.persondetails.Credits
import com.arshia.moviedatademo.data.model.moviedetails.MovieDetails
import com.arshia.moviedatademo.data.model.persondetails.PersonCredits
import com.arshia.moviedatademo.data.model.persondetails.PersonDetails
import com.arshia.moviedatademo.utils.network.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val apiService: DataApiService
) : DataRepositoryInterface {
    override fun nowPlayingMoviePagingSource(genreId: Int?): Flow<PagingData<ItemMovie>> = Pager(
        pagingSourceFactory = { NowPlayingMoviePagingSource(apiService, genreId) },
        config = PagingConfig(pageSize = 1)
    ).flow

    override fun popularMoviePagingSource(genreId: Int?): Flow<PagingData<ItemMovie>> = Pager(
        pagingSourceFactory = { PopularMoviePagingSource(apiService, genreId) },
        config = PagingConfig(pageSize = 1)
    ).flow

    override fun topRatedMoviePagingSource(genreId: Int?): Flow<PagingData<ItemMovie>> = Pager(
        pagingSourceFactory = { TopRatedMoviePagingSource(apiService, genreId) },
        config = PagingConfig(pageSize = 1)
    ).flow

    override fun upcomingMoviePagingSource(genreId: Int?): Flow<PagingData<ItemMovie>> = Pager(
        pagingSourceFactory = { UpcomingMoviePagingSource(apiService, genreId) },
        config = PagingConfig(pageSize = 1)
    ).flow

    override fun searchedMoviePagingSource(searchedKey: String): Flow<PagingData<ItemMovie>> =
        Pager(
            pagingSourceFactory = { SearchedMoviePagingSource(apiService, searchedKey) },
            config = PagingConfig(pageSize = 1)
        ).flow

    override suspend fun genreMovieList(): Flow<DataResult<Genres>> = flow {
        emit(DataResult.Loading)
        try {
            val genreMovieListResult = apiService.genreMovieList()
            emit(DataResult.Success(genreMovieListResult))
        } catch (e: Exception) {
            emit(DataResult.Error(e))
        }
    }

    override suspend fun movieDetails(movieId: Int): Flow<DataResult<MovieDetails>> = flow {
        emit(DataResult.Loading)
        try {
            val movieDetailsResult = apiService.movieDetails(movieId)
            emit(DataResult.Success(movieDetailsResult))
        } catch (e: Exception) {
            emit(DataResult.Error(e))
        }
    }

    override suspend fun recommendedMovieList(movieId: Int): Flow<DataResult<MovieList>> = flow {
        emit(DataResult.Loading)
        try {
            val recommendedMovieResult = apiService.recommendedMovieList(movieId)
            emit(DataResult.Success(recommendedMovieResult))
        } catch (e: Exception) {
            emit(DataResult.Error(e))
        }
    }

    override suspend fun movieCredits(movieId: Int): Flow<DataResult<Credits>> = flow {
        emit(DataResult.Loading)
        try {
            val movieCreditResults = apiService.movieCredits(movieId)
            emit(DataResult.Success(movieCreditResults))
        } catch (e: Exception) {
            emit(DataResult.Error(e))
        }
    }

    override suspend fun personDetails(personId: Int): Flow<DataResult<PersonDetails>> = flow {
        emit(DataResult.Loading)
        try {
            val personDetailsResult = apiService.personDetails(personId)
            emit(DataResult.Success(personDetailsResult))
        } catch (e: Exception) {
            emit(DataResult.Error(e))
        }
    }

    override suspend fun personMovieCredits(personId: Int): Flow<DataResult<PersonCredits>> = flow {
        emit(DataResult.Loading)
        try {
            val personMovieCreditsResult = apiService.personMovieCredits(personId)
            emit(DataResult.Success(personMovieCreditsResult))
        } catch (e: Exception) {
            emit(DataResult.Error(e))
        }
    }
}
