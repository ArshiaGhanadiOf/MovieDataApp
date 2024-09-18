package com.arshia.moviedatademo.ui.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.arshia.moviedatademo.navigation.MainNavHost
import com.arshia.moviedatademo.navigation.ScreenObject
import com.arshia.moviedatademo.navigation.currentDestination
import com.arshia.moviedatademo.ui.screens.mainscreen.component.DrawerComponent
import com.arshia.moviedatademo.ui.screens.mainscreen.component.LostConnectionUI
import com.arshia.moviedatademo.ui.theme.Thunder
import com.arshia.moviedatademo.ui.theme.BlackMarlin
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    isConnected: Boolean
) {
    val mainNavController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val changeDrawerState = fun() {
        scope.launch {
            drawerState.apply {
                if (isClosed) open() else close()
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = BlackMarlin
            ) {
                DrawerComponent(mainNavController, changeDrawerState)
            }
        },
        gesturesEnabled = when (currentDestination(mainNavController)) {
            ScreenObject.NowPlaying.route, ScreenObject.Popular.route, ScreenObject.TopRated.route, ScreenObject.Upcoming.route, ScreenObject.Playlist.route -> {
                true
            }

            else -> {
                false
            }
        }
    ) {
        if (isConnected) {
            MainNavHost(
                navController = mainNavController,
                modifier = Modifier.background(Thunder),
                changeDrawerState
            )
        } else{
            LostConnectionUI()
        }
    }
}


