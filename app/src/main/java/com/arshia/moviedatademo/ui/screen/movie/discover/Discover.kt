package com.arshia.moviedatademo.ui.screens.movie.discover

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arshia.moviedatademo.data.base.BaseGenre
import com.arshia.moviedatademo.data.model.Genre
import com.arshia.moviedatademo.data.model.Genres
import com.arshia.moviedatademo.navigation.ScreenObject
import com.arshia.moviedatademo.navigation.currentDestination
import com.arshia.moviedatademo.ui.screens.movie.component.MovieAppBar
import com.arshia.moviedatademo.ui.screens.movie.component.MovieGenreTaps
import com.arshia.moviedatademo.ui.screens.movie.component.MovieNavigationBar
import com.arshia.moviedatademo.ui.screens.movie.nowplaying.NowPlaying
import com.arshia.moviedatademo.ui.screens.movie.popular.Popular
import com.arshia.moviedatademo.ui.screens.movie.searchcontent.MovieSearchContent
import com.arshia.moviedatademo.ui.screens.movie.toprated.TopRated
import com.arshia.moviedatademo.ui.screens.movie.upcoming.Upcoming
import com.arshia.moviedatademo.ui.theme.Thunder
import com.arshia.moviedatademo.utils.network.DataResult


@SuppressLint("MutableCollectionMutableState")
@Composable
fun MovieDiscover(
    navController: NavController,
    changeDrawerState: () -> Unit
) {
    val discoverViewModel = hiltViewModel<MovieDiscoverViewModel>()
    val genreList = remember { mutableStateOf(arrayListOf<Genre>()) }
    var selectedGenre by remember {
        mutableStateOf(BaseGenre.BASE_DEFAULT_GENRE)
    }
    val onGenreChanged = fun(genre: Genre) {
        selectedGenre = genre
    }

    var searchBarSelected by rememberSaveable {
        mutableStateOf(false)
    }
    val changeSearchBarSelection = fun(selection: Boolean) {
        searchBarSelected = selection
    }
    var searchBarActivated by rememberSaveable {
        mutableStateOf(false)
    }
    val onSearchBarActivationChanged = fun(activation: Boolean) {
        searchBarActivated = activation
    }
    var searchedKey by rememberSaveable {
        mutableStateOf("")
    }
    val onSearchedKeyChanged = fun(newSearchKey: String) {
        searchedKey = newSearchKey
    }
    val resetSearchBar = fun() {
        changeSearchBarSelection(false)
        onSearchBarActivationChanged(false)
        onSearchedKeyChanged("")
    }



    LaunchedEffect(key1 = 0) {
        discoverViewModel.fetchGenreList()
    }
    if (discoverViewModel.genreList.value is DataResult.Success<Genres>) {
        genreList.value =
            (discoverViewModel.genreList.value as DataResult.Success<Genres>).data.genres as ArrayList
        if (genreList.value.first().name != BaseGenre.BASE_DEFAULT_GENRE.name)
            genreList.value.add(0, BaseGenre.BASE_DEFAULT_GENRE)
    }

    Scaffold(
        containerColor = Thunder,
        topBar = {
            MovieAppBar(
                searchBarSelected,
                changeSearchBarSelection,
                searchBarActivated,
                searchedKey,
                onSearchBarActivationChanged,
                onSearchedKeyChanged,
                changeDrawerState
            )
        },
        bottomBar = {
            MovieNavigationBar(navController, onGenreChanged, resetSearchBar)
        },
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
            ) {
                Column {
                    MovieGenreTaps(genreList, selectedGenre, onGenreChanged)
                    when (currentDestination(navController)) {
                        ScreenObject.NowPlaying.route -> {
                            NowPlaying(
                                navController = navController,
                                genre = selectedGenre,
                                resetSearchBar
                            )
                        }

                        ScreenObject.Popular.route -> {
                            Popular(
                                navController = navController,
                                genre = selectedGenre,
                                resetSearchBar
                            )
                        }

                        ScreenObject.TopRated.route -> {
                            TopRated(
                                navController = navController,
                                genre = selectedGenre,
                                resetSearchBar
                            )
                        }

                        ScreenObject.Upcoming.route -> {
                            Upcoming(
                                navController = navController,
                                genre = selectedGenre,
                                resetSearchBar
                            )
                        }
                    }
                }
                if (searchBarActivated) MovieSearchContent(
                    navController,
                    searchedKey,
                    resetSearchBar
                )
            }
        }
    )
}




