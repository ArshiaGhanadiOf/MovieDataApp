package com.arshia.moviedatademo.data.datasource.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arshia.moviedatademo.data.model.favoritemovie.FavoriteMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(favoriteMovie: FavoriteMovie)

    @Delete
    suspend fun deleteMovie(favoriteMovie: FavoriteMovie)

    @Query("SELECT * FROM favorite_movies ORDER BY id DESC")
    fun getAllFavoriteMovies(): Flow<List<FavoriteMovie>>

    @Query("SELECT * FROM FAVORITE_MOVIES WHERE movie_id LIKE ( :movieId ) LIMIT 1")
    fun findMovieById(movieId: Int): Flow<FavoriteMovie>
}