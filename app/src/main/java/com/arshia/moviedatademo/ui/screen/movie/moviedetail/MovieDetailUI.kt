package com.arshia.moviedatademo.ui.screen.movie.moviedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arshia.moviedatademo.R
import com.arshia.moviedatademo.data.base.BaseApiURL
import com.arshia.moviedatademo.data.model.ItemMovie
import com.arshia.moviedatademo.data.model.MovieList
import com.arshia.moviedatademo.data.model.moviedetails.MovieDetails
import com.arshia.moviedatademo.data.model.persondetails.Cast
import com.arshia.moviedatademo.data.model.persondetails.Credits
import com.arshia.moviedatademo.data.model.persondetails.Crew
import com.arshia.moviedatademo.navigation.ScreenObject
import com.arshia.moviedatademo.navigation.parentDestination
import com.arshia.moviedatademo.ui.screens.movie.component.ArrowBackBackAppBar
import com.arshia.moviedatademo.ui.screens.movie.component.ExpandingText
import com.arshia.moviedatademo.ui.screens.movie.moviedetail.MovieDetailsViewModel
import com.arshia.moviedatademo.ui.theme.LightningYellow
import com.arshia.moviedatademo.ui.theme.DetailsBackgroundBrush
import com.arshia.moviedatademo.ui.theme.DeepMagenta
import com.arshia.moviedatademo.utils.hourMinutes
import com.arshia.moviedatademo.utils.network.DataResult
import com.arshia.moviedatademo.utils.roundTo
import com.arshia.moviedatademo.utils.shadow
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieDetailUI(navController: NavController, movieId: Int) {
    val viewModel = hiltViewModel<MovieDetailsViewModel>()
    val movieDetails = viewModel.movieDetails
    val recommendedMovieList = viewModel.recommendedMovieList
    val movieCredits = viewModel.movieCredits
    var addedToPlaylist by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(true) {
        viewModel.fetchMovieDetails(movieId)
        viewModel.fetchMovieCredits(movieId)
        viewModel.fetchRecommendedMovieList(movieId)
        viewModel.findMovieById(movieId)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = DeepMagenta
            )
    ) {
        movieDetails.value?.let { movieDetailsData ->
            if (movieDetailsData is DataResult.Success<MovieDetails>) {
                addedToPlaylist = viewModel.movieInPlayList.value != null
                ArrowBackBackAppBar(
                    title = stringResource(id = R.string.movie_detail),
                    onArrowBackClick = { navController.popBackStack() },
                    onTitleClick = {
                        navController.navigate(parentDestination(navController)!!)
                    },
                    movieDetails = true,
                    favoriteMovie = addedToPlaylist,
                    onFavoriteClick = {
                        if (addedToPlaylist) {
                            viewModel.removeFromPlaylist(viewModel.movieInPlayList.value!!)
                            addedToPlaylist = false
                        } else {
                            viewModel.addToPlayList(movieDetailsData.data)
                            addedToPlaylist = true
                        }
                    }
                )
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = LightningYellow
                            )
                            .padding(bottom = 16.dp)
                            .shadow(
                                spread = 2f.dp,
                                blurRadius = 8.dp
                            )
                    ) {
                        GlideImage(
                            modifier = Modifier
                                .fillMaxWidth(),
                            alignment = Alignment.TopCenter,
                            contentScale = ContentScale.FillWidth,
                            model = BaseApiURL.BASE_IMAGE_API_URL.plus(movieDetailsData.data.backdropPath),
                            contentDescription = movieDetailsData.data.title,
                            failure = placeholder(R.drawable.no_image)
                        )
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .padding(bottom = 2.dp)
                                .align(Alignment.BottomCenter)
                        ) {
                            GlideImage(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .shadow(
                                        spread = 8f.dp,
                                        blurRadius = 20.dp
                                    ),
                                model = BaseApiURL.BASE_IMAGE_API_URL.plus(movieDetailsData.data.posterPath),
                                contentDescription = movieDetailsData.data.title,
                            ) {
                                it.transform(CenterInside(), RoundedCorners(32))
                            }
                        }

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(brush = DetailsBackgroundBrush)
                    ) {
                        MovieTitle(title = movieDetailsData.data.title)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .background(
                                    color = Color.Gray.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(10.dp)
                                ),
                        ) {
                            Column(
                                Modifier
                                    .weight(1f)
                                    .padding(5.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.language),
                                    style = TextStyle(
                                        fontFamily = FontFamily.Default,
                                        color = Color(0xFF212121),
                                        fontSize = 12.sp,
                                    )
                                )
                                Text(
                                    text = movieDetailsData.data.originalLanguage,
                                    style = TextStyle(
                                        fontFamily = FontFamily.Default,
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                            Column(
                                Modifier
                                    .weight(1f)
                                    .padding(5.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.rating),
                                    style = TextStyle(
                                        fontFamily = FontFamily.Default,
                                        color = Color(0xFF212121),
                                        fontSize = 12.sp,
                                    )
                                )
                                Text(
                                    text = movieDetailsData.data.voteAverage.roundTo(1).toString(),
                                    style = TextStyle(
                                        fontFamily = FontFamily.Default,
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                            Column(
                                Modifier
                                    .weight(1f)
                                    .padding(5.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.duration),
                                    style = TextStyle(
                                        fontFamily = FontFamily.Default,
                                        color = Color(0xFF212121),
                                        fontSize = 12.sp,
                                    )
                                )
                                Text(
                                    text = movieDetailsData.data.runtime.hourMinutes(),
                                    style = TextStyle(
                                        fontFamily = FontFamily.Default,
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                            Column(
                                Modifier
                                    .weight(1f)
                                    .padding(5.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.release_date),
                                    style = TextStyle(
                                        fontFamily = FontFamily.Default,
                                        color = Color(0xFF212121),
                                        fontSize = 12.sp,
                                    )
                                )
                                Text(
                                    text = movieDetailsData.data.releaseDate,
                                    style = TextStyle(
                                        fontFamily = FontFamily.Default,
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .background(
                                    color = Color.Gray.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(10.dp)
                                ),
                        ) {
                            Text(
                                text = "Overview",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                color = Color.Black,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.W500,
                            )
                            HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(5.dp))
                            ExpandingText(
                                text = movieDetailsData.data.overview,
                                modifier = Modifier.padding(5.dp)
                            )
                        }

                        movieCredits.value?.let { movieCreditData ->
                            if (movieCreditData is DataResult.Success<Credits>) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp)
                                        .background(
                                            color = Color.Gray.copy(alpha = 0.5f),
                                            shape = RoundedCornerShape(10.dp)
                                        ),
                                ) {
                                    Text(
                                        text = "Cast",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(5.dp),
                                        color = Color.Black,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.W500,
                                    )
                                    HorizontalDivider(
                                        thickness = 2.dp,
                                        modifier = Modifier.padding(5.dp)
                                    )
                                    LazyRow(modifier = Modifier.fillMaxHeight()) {
                                        items(movieCreditData.data.cast, itemContent = { cast ->
                                            CastView(cast, navController)
                                        })
                                    }
                                }

                                recommendedMovieList.value?.let { recommendedMovieListData ->
                                    if (recommendedMovieListData is DataResult.Success<MovieList>) {
                                        if (recommendedMovieListData.data.results.isNotEmpty()) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(5.dp)
                                                    .background(
                                                        color = Color.Gray.copy(alpha = 0.5f),
                                                        shape = RoundedCornerShape(10.dp)
                                                    ),
                                            ) {
                                                Text(
                                                    text = "Recommended Movie",
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(5.dp),
                                                    color = Color.Black,
                                                    fontSize = 24.sp,
                                                    fontWeight = FontWeight.W500,
                                                )
                                                HorizontalDivider(
                                                    thickness = 2.dp,
                                                    modifier = Modifier.padding(5.dp)
                                                )
                                                LazyRow(modifier = Modifier.fillMaxHeight()) {
                                                    items(
                                                        recommendedMovieListData.data.results,
                                                        itemContent = { itemMovie ->
                                                            MovieView(
                                                                itemMovie = itemMovie,
                                                                navController = navController
                                                            )
                                                        })
                                                }
                                            }
                                        }
                                    }
                                }

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 5.dp,
                                            end = 5.dp,
                                            top = 5.dp,
                                            bottom = 24.dp
                                        )
                                        .background(
                                            color = Color.Gray.copy(alpha = 0.5f),
                                            shape = RoundedCornerShape(10.dp)
                                        ),
                                ) {
                                    Text(
                                        text = "Crew",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(5.dp),
                                        color = Color.Black,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.W500,
                                    )
                                    HorizontalDivider(
                                        thickness = 2.dp,
                                        modifier = Modifier.padding(5.dp)
                                    )
                                    LazyRow(modifier = Modifier.fillMaxHeight()) {
                                        items(movieCreditData.data.crew, itemContent = { crew ->

                                            CrewView(crew = crew, navController = navController)
                                        })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MovieTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp),
        color = Color.Black,
        fontSize = 30.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.W700,
        overflow = TextOverflow.Visible,
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CastView(
    cast: Cast,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .padding(5.dp)
            .clickable {
                navController.navigate(
                    ScreenObject.PersonDetails.route.plus(
                        "/${cast.id}"
                    )
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            modifier = Modifier
                .padding(5.dp)
                .size(80.dp),
            model = BaseApiURL.BASE_IMAGE_API_URL.plus(cast.profilePath),
            contentDescription = "cast",
            failure = placeholder(R.drawable.no_credit)
        ) {
            it.circleCrop()
        }
        Text(
            text = cast.name,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                color = Color(0xFF212121),
                fontSize = 14.sp,
            )
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CrewView(crew: Crew, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(5.dp)
            .clickable {
                navController.navigate(
                    ScreenObject.PersonDetails.route.plus(
                        "/${crew.id}"
                    )
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = crew.job,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                color = Color(0xFF212121),
                fontSize = 14.sp,
            )
        )
        GlideImage(
            modifier = Modifier
                .padding(5.dp)
                .size(80.dp),
            model = BaseApiURL.BASE_IMAGE_API_URL.plus(crew.profilePath),
            contentDescription = "crew",
            failure = placeholder(R.drawable.no_credit)
        ) {
            it.circleCrop()
        }
        Text(
            text = crew.name,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                color = Color(0xFF212121),
                fontSize = 14.sp,
            )
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieView(itemMovie: ItemMovie, navController: NavController) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .padding(8.dp)
            .clickable {
                navController.navigate(
                    ScreenObject.MovieDetail.route.plus(
                        "/${itemMovie.id}"
                    )
                )
            }
    ) {
        GlideImage(
            modifier = Modifier
                .wrapContentSize(),
            model = BaseApiURL.BASE_IMAGE_API_URL.plus(
                itemMovie.posterPath
            ),
            contentDescription = itemMovie.title,
            failure = placeholder(R.drawable.no_image)
        ) {
            it.transform(
                CenterInside(),
                RoundedCorners(32)
            )
        }
    }
}