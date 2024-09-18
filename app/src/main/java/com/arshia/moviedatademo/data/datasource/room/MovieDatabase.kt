package com.arshia.moviedatademo.data.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arshia.moviedatademo.data.model.favoritemovie.FavoriteMovie

@Database(
    entities = [FavoriteMovie::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}