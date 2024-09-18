package com.arshia.moviedatademo.ui.screens.mainscreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arshia.moviedatademo.navigation.ScreenObject
import com.arshia.moviedatademo.navigation.currentDestination
import com.arshia.moviedatademo.ui.theme.LightningYellow
import com.arshia.moviedatademo.ui.theme.MulberryWood
import com.arshia.moviedatademo.ui.theme.Nugget

@Composable
fun DrawerComponent(
    navController: NavController,
    changeDrawerState: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val drawerItems = listOf(
        ScreenObject.Movie,
        ScreenObject.Playlist
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .width((3 * configuration.screenWidthDp / 4).dp)
    ) {
        Spacer(
            modifier = Modifier
                .padding(24.dp)
        )
        drawerItems.forEach { item ->
            DrawerItem(
                text = stringResource(item.title),
                selected = currentDestination(navController) == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    changeDrawerState()
                }
            )
        }
    }
}

@Composable
private fun DrawerItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val background = if (selected) {
        Modifier
            .background(MulberryWood)
    } else {
        Modifier
            .clickable(onClick = {
                onClick()
            })
    }
    Box(
        modifier = Modifier
            .height(60.dp)
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .clip(CircleShape)
            .then(background),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium,
            color = if (selected) {
                LightningYellow
            } else {
                Nugget
            },
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}