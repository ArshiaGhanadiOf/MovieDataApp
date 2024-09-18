package com.arshia.moviedatademo.ui.screens.movie.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.inputFieldColors
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.arshia.moviedatademo.R
import com.arshia.moviedatademo.ui.theme.Thunder
import com.arshia.moviedatademo.ui.theme.LightningYellow
import com.arshia.moviedatademo.ui.theme.Kumera
import com.arshia.moviedatademo.ui.theme.BlackMarlin
import com.arshia.moviedatademo.ui.theme.Nugget

@Composable
fun MovieAppBar(
    searchBarSelected: Boolean,
    changeSearchBarSelection: (Boolean) -> Unit,
    searchBarActivated: Boolean,
    searchedKey: String,
    onSearchBarActivationChanged: (Boolean) -> Unit,
    onSearchedKeyChanged: (String) -> Unit,
    changeDrawerState: () -> Unit
) {
    AnimatedContent(
        searchBarSelected,
        transitionSpec = {
            when (searchBarSelected) {
                false -> {
                    (fadeIn(animationSpec = tween(500, delayMillis = 100))).togetherWith(
                        slideOutHorizontally(
                            animationSpec = tween(300, easing = FastOutLinearInEasing),
                        ) { fullWidth ->
                            fullWidth
                        }
                    )
                }

                true -> {
                    (slideInHorizontally(
                        animationSpec = tween(600, easing = FastOutLinearInEasing),
                    ) { fullWidth ->
                        fullWidth
                    }).togetherWith(
                        fadeOut(
                            animationSpec = tween(600)
                        )
                    )
                }
            }
        },
        modifier = Modifier,
        label = "Animated Content"
    ) { targetState ->
        when (targetState) {
            false -> {
                MovieTopAppBar(
                    changeSearchBarSelection,
                    changeDrawerState
                )
            }

            true -> {
                MovieSearchBar(
                    changeSearchBarSelection,
                    searchBarActivated,
                    searchedKey,
                    onSearchedKeyChanged,
                    onSearchBarActivationChanged
                )
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchBar(
    changeSearchBarSelection: (Boolean) -> Unit,
    searchBarActivated: Boolean,
    searchedKey: String,
    onSearchedKeyChanged: (String) -> Unit,
    onSearchBarActivationChanged: (Boolean) -> Unit
) {

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(Thunder),
        colors = SearchBarDefaults.colors(
            containerColor = BlackMarlin,
            inputFieldColors = inputFieldColors(
                focusedTextColor = Nugget
            )
        ),
        query = searchedKey,
        placeholder = { Text(text = "Search", color = Kumera) },
        onQueryChange = {
            onSearchedKeyChanged(it)
            onSearchBarActivationChanged(true)
        },
        onSearch = {},
        active = false,
        shape = if (searchBarActivated) SearchBarDefaults.fullScreenShape else SearchBarDefaults.inputFieldShape,
        onActiveChange = {
            onSearchBarActivationChanged(it)
        },
        trailingIcon = {
            IconButton(onClick = {
                onSearchedKeyChanged("")
                onSearchBarActivationChanged(false)
                changeSearchBarSelection(false)
            }) {
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = LightningYellow)
            }
        },
        leadingIcon = {
            IconButton(onClick = {
                if (searchedKey.isNotEmpty()) {
                    onSearchedKeyChanged("")
                } else {
                    onSearchBarActivationChanged(true)
                }
            }) {
                Icon(
                    if (searchedKey.isEmpty()) Icons.Default.Search else Icons.Default.Close,
                    contentDescription = null,
                    tint = LightningYellow
                )
            }
        },
    ) {
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopAppBar(
    changeSearchBarSelection: (Boolean) -> Unit,
    changeDrawerState: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        modifier = Modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Thunder,
            titleContentColor = LightningYellow,
            scrolledContainerColor = LightningYellow,
            navigationIconContentColor = LightningYellow,
            actionIconContentColor = LightningYellow
        ),
        navigationIcon = {
            IconButton(
                onClick = {
                    changeDrawerState()
                },
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "menu",
                )
            }
        },
        actions = {
            IconButton(
                onClick = { changeSearchBarSelection(true) },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(Icons.Filled.Search, contentDescription = "search")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArrowBackBackAppBar(
    title: String,
    onArrowBackClick: () -> Unit,
    onTitleClick: () -> Unit,
    movieDetails: Boolean = false,
    favoriteMovie: Boolean = false,
    onFavoriteClick: () -> Unit = {}
) {
    var clickable by rememberSaveable {
        mutableStateOf(true)
    }
    if (movieDetails) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    title,
                    modifier = Modifier
                        .clickable(enabled = clickable){
                            clickable = false
                            onTitleClick()
                        }
                )
            },
            modifier = Modifier.zIndex(100f),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Thunder,
                titleContentColor = LightningYellow,
                scrolledContainerColor = LightningYellow,
                navigationIconContentColor = LightningYellow,
                actionIconContentColor = LightningYellow
            ),
            navigationIcon = {
                IconButton(
                    enabled = clickable,
                    onClick = {
                        clickable = false
                        onArrowBackClick()
                    },
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "ArrowBack",
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = { onFavoriteClick() },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        if (favoriteMovie) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "ArrowBack",
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    )
                }
            }
        )
    } else {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    title,
                    modifier = Modifier
                        .clickable(enabled = clickable){
                            clickable = false
                            onTitleClick()
                        }
                )
            },
            modifier = Modifier.zIndex(100f),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Thunder,
                titleContentColor = LightningYellow,
                scrolledContainerColor = LightningYellow,
                navigationIconContentColor = LightningYellow,
                actionIconContentColor = LightningYellow
            ),
            navigationIcon = {
                IconButton(
                    enabled = clickable,
                    onClick = {
                        clickable = false
                        onArrowBackClick()
                    },
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "ArrowBack",
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    )
                }
            },
//        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistAppBar(
    changeDrawerState: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.playlist)) },
        modifier = Modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Thunder,
            titleContentColor = LightningYellow,
            scrolledContainerColor = LightningYellow,
            navigationIconContentColor = LightningYellow,
            actionIconContentColor = LightningYellow
        ),
        navigationIcon = {
            IconButton(
                onClick = {
                    changeDrawerState()
                },
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "menu",
                )
            }
        },
    )
}

