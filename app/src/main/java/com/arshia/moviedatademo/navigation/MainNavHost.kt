package com.arshia.moviedatademo.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.arshia.moviedatademo.R
import com.arshia.moviedatademo.ui.screens.movie.discover.MovieDiscover
import com.arshia.moviedatademo.ui.screens.movie.moviedetail.MovieDetailUI
import com.arshia.moviedatademo.ui.screens.people.persondetail.PersonDetailUI
import com.arshia.moviedatademo.ui.screens.playlist.PlaylistUI
import kotlinx.coroutines.CoroutineScope

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier,
    changeDrawerState: () -> Unit
) {
    NavHost(navController, startDestination = ScreenObject.NowPlaying.route, modifier) {
        composable(ScreenObject.NowPlaying.route) {
            MovieDiscover(navController, changeDrawerState)
        }

        composable(ScreenObject.Popular.route) {
            MovieDiscover(navController, changeDrawerState)
        }

        composable(ScreenObject.TopRated.route) {
            MovieDiscover(navController, changeDrawerState)
        }

        composable(ScreenObject.Upcoming.route) {
            MovieDiscover(navController, changeDrawerState)
        }

        composable(
            ScreenObject.MovieDetail.route.plus(ScreenObject.MovieDetail.objectPath),
            arguments = listOf(navArgument(ScreenObject.MovieDetail.objectName!!) {
                type = NavType.IntType
            })
        ) {
            label = stringResource(R.string.movie_detail)
            val movieId = it.arguments?.getInt(ScreenObject.MovieDetail.objectName)
            movieId?.let {
                MovieDetailUI(
                    navController = navController, movieId
                )
            }
        }

        composable(
            ScreenObject.PersonDetails.route.plus(ScreenObject.PersonDetails.objectPath),
            arguments = listOf(navArgument(ScreenObject.PersonDetails.objectName!!) {
                type = NavType.IntType
            })
        ) {
            label = stringResource(R.string.people)
            val personId = it.arguments?.getInt(ScreenObject.PersonDetails.objectName)
            personId?.let {
                PersonDetailUI(
                    navController = navController, personId
                )
            }
        }

        composable(ScreenObject.Playlist.route) {
            PlaylistUI(navController, changeDrawerState)
        }
    }
}
