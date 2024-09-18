package com.arshia.moviedatademo.data.model.favoritemovie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovie(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("movie_id") val movieId: Int,
    @ColumnInfo("poster_path") val posterPath: String
)
