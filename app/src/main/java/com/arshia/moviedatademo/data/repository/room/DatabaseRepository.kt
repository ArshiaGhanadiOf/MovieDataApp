package com.arshia.moviedatademo.data.repository.room

import com.arshia.moviedatademo.data.datasource.room.MovieDao
import com.arshia.moviedatademo.data.model.favoritemovie.FavoriteMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepository @Inject constructor(
    private val movieDao: MovieDao
) : DatabaseRepositoryInterface {
    override suspend fun insertMovie(favoriteMovie: FavoriteMovie) {
        movieDao.insertMovie(favoriteMovie)
    }

    override suspend fun deleteMovie(favoriteMovie: FavoriteMovie) {
        movieDao.deleteMovie(favoriteMovie)
    }

    override suspend fun getAllFavoriteMovie(): Flow<List<FavoriteMovie>> =
        movieDao.getAllFavoriteMovies()

    override suspend fun findMovieById(movieId: Int): Flow<FavoriteMovie> = movieDao.findMovieById(movieId)
}