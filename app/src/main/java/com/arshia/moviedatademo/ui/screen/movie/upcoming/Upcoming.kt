package com.arshia.moviedatademo.ui.screens.movie.upcoming

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.arshia.moviedatademo.data.model.Genre
import com.arshia.moviedatademo.data.model.ItemMovie
import com.arshia.moviedatademo.ui.screens.movie.component.MovieItemView
import com.arshia.moviedatademo.ui.screens.movie.component.items
import com.arshia.moviedatademo.ui.theme.BackgroundBrush
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update

@Composable
fun Upcoming(
    navController: NavController,
    genre: Genre,
    resetSearchBar: () -> Unit
) {
    val viewModel = hiltViewModel<UpcomingViewModel>()
    viewModel.selectedGenre.update { genre }
    val movieList: Flow<PagingData<ItemMovie>> = viewModel.upcomingMovieList
    val movieItemList: LazyPagingItems<ItemMovie> = movieList.collectAsLazyPagingItems()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.background(BackgroundBrush),
        state = LazyGridState(0),
        content = {
            items(movieItemList) { item ->
                item?.let {
                    MovieItemView(navController, item, resetSearchBar)
                }
            }
        },
    )
}