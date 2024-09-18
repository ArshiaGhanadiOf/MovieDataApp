package com.arshia.moviedatademo.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun currentDestination(navController: NavController): String? {
    val currentBackStack by navController.currentBackStackEntryAsState()
    return currentBackStack?.destination?.route?.substringBeforeLast("/")
}

@SuppressLint("StateFlowValueCalledInComposition", "RestrictedApi")
fun parentDestination(navController: NavController): String? {
    navController.currentBackStack.value.reversed().forEach {
        if (it.destination.route == ScreenObject.NowPlaying.route || it.destination.route == ScreenObject.Popular.route || it.destination.route == ScreenObject.TopRated.route || it.destination.route == ScreenObject.Upcoming.route || it.destination.route == ScreenObject.Playlist.route) {
            return it.destination.route
        }
    }
    return null
}