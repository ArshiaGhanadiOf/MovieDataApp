package com.arshia.moviedatademo.data.repository.room

import com.arshia.moviedatademo.data.model.favoritemovie.FavoriteMovie
import kotlinx.coroutines.flow.Flow

interface DatabaseRepositoryInterface {
    suspend fun insertMovie(favoriteMovie: FavoriteMovie)
    suspend fun deleteMovie(favoriteMovie: FavoriteMovie)
    suspend fun getAllFavoriteMovie(): Flow<List<FavoriteMovie>>
    suspend fun findMovieById(movieId: Int): Flow<FavoriteMovie>
}