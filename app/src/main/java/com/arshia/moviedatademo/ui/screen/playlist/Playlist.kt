package com.arshia.moviedatademo.ui.screens.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.arshia.moviedatademo.R
import com.arshia.moviedatademo.data.base.BaseApiURL
import com.arshia.moviedatademo.data.model.favoritemovie.FavoriteMovie
import com.arshia.moviedatademo.navigation.ScreenObject
import com.arshia.moviedatademo.ui.screens.movie.component.PlaylistAppBar
import com.arshia.moviedatademo.ui.theme.LightningYellow
import com.arshia.moviedatademo.ui.theme.BackgroundBrush
import com.arshia.moviedatademo.ui.theme.failureBackgroundBrush
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@Composable
fun PlaylistUI(
    navController: NavController,
    changeDrawerState: () -> Unit
) {
    val viewModel = hiltViewModel<PlaylistViewModel>()
    viewModel.playlist
    val content by viewModel.playlist.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        viewModel.getPlaylist()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PlaylistAppBar(changeDrawerState)
        if (content.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = BackgroundBrush),
                state = LazyGridState(0),
                content = {
                    items(content) { item ->
                        PlayListItemView(navController = navController, item = item)
                    }
                },
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = failureBackgroundBrush),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Playlist is Empty...",
                    color = LightningYellow,
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayListItemView(
    navController: NavController,
    item: FavoriteMovie,
) {
    Column(modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 15.dp, bottom = 15.dp)) {
        GlideImage(
            modifier = Modifier
                .size(300.dp)
                .clickable {
                    navController.navigate(ScreenObject.MovieDetail.route.plus("/${item.movieId}"))
                },
            model = BaseApiURL.BASE_IMAGE_API_URL.plus(item.posterPath),
            contentDescription = null,
            failure = placeholder(R.drawable.no_image)
        ) {
            it.transform(CenterInside(), RoundedCorners(32))
        }
    }
}