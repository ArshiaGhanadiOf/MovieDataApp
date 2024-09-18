package com.arshia.moviedatademo.di

import com.arshia.moviedatademo.data.datasource.remote.DataApiService
import com.arshia.moviedatademo.data.datasource.room.MovieDao
import com.arshia.moviedatademo.data.repository.remote.DataRepository
import com.arshia.moviedatademo.data.repository.room.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideDataRepository(
        apiService: DataApiService
    ): DataRepository {
        return DataRepository(
            apiService
        )
    }

    @Singleton
    @Provides
    fun provideDatabaseRepository(
        movieDao: MovieDao
    ): DatabaseRepository {
        return DatabaseRepository(movieDao)
    }
}
