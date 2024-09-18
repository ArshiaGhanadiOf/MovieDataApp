package com.arshia.moviedatademo.ui.screens.movie.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arshia.moviedatademo.data.base.BaseGenre
import com.arshia.moviedatademo.data.model.Genre
import com.arshia.moviedatademo.navigation.ScreenObject
import com.arshia.moviedatademo.navigation.currentDestination
import com.arshia.moviedatademo.ui.theme.Thunder
import com.arshia.moviedatademo.ui.theme.LightningYellow
import com.arshia.moviedatademo.ui.theme.Kumera
import com.arshia.moviedatademo.ui.theme.MulberryWood

@Composable
fun MovieNavigationBar(
    navController: NavController,
    onGenreChanged: (Genre) -> Unit,
    resetSearchBar: () -> Unit
) {
    val items = listOf(
        ScreenObject.NowPlaying,
        ScreenObject.Popular,
        ScreenObject.TopRated,
        ScreenObject.Upcoming
    )
    NavigationBar(
        containerColor = Thunder
    ) {
        items.forEach { item ->
            val selected = currentDestination(navController) == item.route
            NavigationBarItem(
                label = { Text(text = stringResource(id = item.title), fontSize = 10.sp) },
                selected = selected,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = LightningYellow,
                    selectedTextColor = LightningYellow,
                    indicatorColor = MulberryWood,
                    unselectedIconColor = Kumera,
                    unselectedTextColor = Kumera
                ),
                icon = {
                    if (selected) {
                        if (item.navIconSelected != null) {
                            Icon(
                                painter = painterResource(id = item.navIconSelected!!),
                                contentDescription = stringResource(id = item.title),
                                modifier = Modifier.size(32.dp)
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = item.navIcon!!),
                                contentDescription = stringResource(id = item.title),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    } else {
                        Icon(
                            painter = painterResource(id = item.navIcon!!),
                            contentDescription = stringResource(id = item.title),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
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
                    onGenreChanged(BaseGenre.BASE_DEFAULT_GENRE)
                    resetSearchBar()
                }
            )
        }

    }
}