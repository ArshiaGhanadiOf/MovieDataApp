package com.arshia.moviedatademo.data.datasource.remote

import com.arshia.moviedatademo.data.base.BaseApiURL
import com.arshia.moviedatademo.data.model.Genres
import com.arshia.moviedatademo.data.model.moviedetails.MovieDetails
import com.arshia.moviedatademo.data.model.MovieList
import com.arshia.moviedatademo.data.model.persondetails.Credits
import com.arshia.moviedatademo.data.model.persondetails.PersonCredits
import com.arshia.moviedatademo.data.model.persondetails.PersonDetails
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface DataApiService {
    @GET("movie/now_playing")
    suspend fun nowPlayingMovieList(
        @Query("page") page: Int,
        @Header("Authorization") authorization: String = BaseApiURL.AUTHORIZATION,
        @Header("accept") accept: String = BaseApiURL.ACCEPT
    ): MovieList

    @GET("movie/popular")
    suspend fun popularMovieList(
        @Query("page") page: Int,
        @Header("Authorization") authorization: String = BaseApiURL.AUTHORIZATION,
        @Header("accept") accept: String = BaseApiURL.ACCEPT
    ): MovieList

    @GET("movie/top_rated")
    suspend fun topRatedMovieList(
        @Query("page") page: Int,
        @Header("Authorization") authorization: String = BaseApiURL.AUTHORIZATION,
        @Header("accept") accept: String = BaseApiURL.ACCEPT
    ): MovieList

    @GET("movie/upcoming")
    suspend fun upcomingMovieList(
        @Query("page") page: Int,
        @Header("Authorization") authorization: String = BaseApiURL.AUTHORIZATION,
        @Header("accept") accept: String = BaseApiURL.ACCEPT
    ): MovieList

    @GET("search/movie?include_adult=false&language=en-US")
    suspend fun searchedMovieList(
        @Query("page") page: Int,
        @Query("query") searchedKey: String,
        @Header("Authorization") authorization: String = BaseApiURL.AUTHORIZATION,
        @Header("accept") accept: String = BaseApiURL.ACCEPT
    ): MovieList

    @GET("genre/movie/list")
    suspend fun genreMovieList(
        @Header("Authorization") authorization: String = BaseApiURL.AUTHORIZATION,
        @Header("accept") accept: String = BaseApiURL.ACCEPT
    ): Genres

    @GET("movie/{movieId}")
    suspend fun movieDetails(
        @Path("movieId") movieId: Int,
        @Header("Authorization") authorization: String = BaseApiURL.AUTHORIZATION,
        @Header("accept") accept: String = BaseApiURL.ACCEPT
    ): MovieDetails

    @GET("movie/{movieId}/recommendations")
    suspend fun recommendedMovieList(
        @Path("movieId") movieId: Int,
        @Header("Authorization") authorization: String = BaseApiURL.AUTHORIZATION,
        @Header("accept") accept: String = BaseApiURL.ACCEPT
    ): MovieList

    @GET("movie/{movieId}/credits")
    suspend fun movieCredits(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int = 1,
        @Header("Authorization") authorization: String = BaseApiURL.AUTHORIZATION,
        @Header("accept") accept: String = BaseApiURL.ACCEPT
    ): Credits

    @GET("person/{personId}")
    suspend fun personDetails(
        @Path("personId") personId: Int,
        @Header("Authorization") authorization: String = BaseApiURL.AUTHORIZATION,
        @Header("accept") accept: String = BaseApiURL.ACCEPT
    ): PersonDetails

    @GET("person/{personId}/movie_credits")
    suspend fun personMovieCredits(
        @Path("personId") personId: Int,
        @Header("Authorization") authorization: String = BaseApiURL.AUTHORIZATION,
        @Header("accept") accept: String = BaseApiURL.ACCEPT
    ): PersonCredits
}