package com.arshia.moviedatademo.navigation

import com.arshia.moviedatademo.R


sealed class ScreenObject(
    override val route: String,
    override val title: Int = R.string.app_name,
    override val navIcon: Int? = null,
    override val navIconSelected: Int? = null,
    override val objectName: String? = null,
    override val objectPath: String? = null
) : ScreenInterface {
    object Movie :
        ScreenObject(
            "now_playing_movie",
            R.string.movie
        )

    object NowPlaying :
        ScreenObject(
            "now_playing_movie",
            R.string.now_playing,
            R.drawable.now_playing_outline,
            R.drawable.now_playing
        )

    object Popular : ScreenObject(
        "popular_movie",
        R.string.popular,
        R.drawable.popular_outline,
        R.drawable.popular
    )

    object TopRated :
        ScreenObject(
            "top_rated_movie",
            R.string.top_rated,
            R.drawable.top_rated_outline,
            R.drawable.top_rated
        )

    object Upcoming :
        ScreenObject(
            "upcoming_movie",
            R.string.upcoming,
            R.drawable.upcoming
        )


    object MovieDetail :
        ScreenObject("movie_item_detail", R.string.app_name, null, null, "movieId", "/{movieId}")


    object Playlist :
        ScreenObject(
            "playlist",
            title = R.string.playlist,
        )

    object PersonDetails :
        ScreenObject("person_item_details", R.string.people, null, null, "personId", "/{personId}")


}