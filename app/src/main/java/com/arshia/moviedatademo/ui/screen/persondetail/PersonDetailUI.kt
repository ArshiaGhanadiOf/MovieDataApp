package com.arshia.moviedatademo.ui.screens.people.persondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arshia.moviedatademo.R
import com.arshia.moviedatademo.data.base.BaseApiURL
import com.arshia.moviedatademo.data.model.persondetails.PersonCredits
import com.arshia.moviedatademo.data.model.persondetails.PersonDetails
import com.arshia.moviedatademo.navigation.parentDestination
import com.arshia.moviedatademo.ui.screens.movie.component.ArrowBackBackAppBar
import com.arshia.moviedatademo.ui.screens.movie.component.ExpandingText
import com.arshia.moviedatademo.ui.screens.movie.moviedetail.MovieView
import com.arshia.moviedatademo.ui.theme.DetailsBackgroundBrush
import com.arshia.moviedatademo.utils.genderValueToString
import com.arshia.moviedatademo.utils.network.DataResult
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PersonDetailUI(navController: NavController, personId: Int) {
    val viewModel = hiltViewModel<PersonDetailsViewModel>()
    val personDetails = viewModel.personDetails
    val personMovieCredits = viewModel.personMovieCredits
    LaunchedEffect(true) {
        viewModel.fetchPersonDetails(personId)
        viewModel.fetchPersonMovieCredits(personId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        personDetails.value?.let { personDetailsData ->
            if (personDetailsData is DataResult.Success<PersonDetails>) {
                ArrowBackBackAppBar(
                    title = stringResource(id = R.string.people),
                    onArrowBackClick = { navController.popBackStack() },
                    onTitleClick = {
                        navController.navigate(parentDestination(navController)!!)
                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .background(brush = DetailsBackgroundBrush)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, end = 5.dp, bottom = 5.dp, start = 5.dp)
                            .background(
                                color = Color.Gray.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(10.dp)
                            ),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(4f)
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            GlideImage(
                                modifier = Modifier
                                    .fillMaxSize(),
                                model = BaseApiURL.BASE_IMAGE_API_URL.plus(personDetailsData.data.profilePath),
                                contentDescription = personDetailsData.data.name,
                                contentScale = ContentScale.FillWidth,
                                failure = placeholder(R.drawable.no_credit)
                            ) {
                                it.transform(CenterInside(), RoundedCorners(32))
                            }
                        }
                        Column(
                            modifier = Modifier
                                .weight(5f)
                                .padding(5.dp),
                        ) {
                            Text(
                                modifier = Modifier.padding(
                                    start = 5.dp,
                                    top = 5.dp,
                                    end = 5.dp,
                                    bottom = 10.dp
                                ),
                                text = personDetailsData.data.name,
                                color = Color.White,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Medium
                            )
                            PersonalInfo(
                                title = "Known for",
                                info = personDetailsData.data.knownForDepartment
                            )
                            PersonalInfo(
                                title = "Gender",
                                info = personDetailsData.data.gender.genderValueToString()
                            )
                            personDetailsData.data.birthday?.let { birthday ->
                                PersonalInfo(title = "Birthday", info = birthday)
                            }
                            personDetailsData.data.placeOfBirth?.let { placeOfBirth ->
                                PersonalInfo(title = "Place of Birth", info = placeOfBirth)
                            }
                        }
                    }
                    if (personDetailsData.data.biography.isNotEmpty()) {
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
                                text = "Biography",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                color = Color.Black,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.W500,
                            )
                            HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(5.dp))
                            ExpandingText(
                                text = personDetailsData.data.biography,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }
                    personMovieCredits.value?.let { personMovieCreditsData ->
                        if (personMovieCreditsData is DataResult.Success<PersonCredits>) {
                            if (personMovieCreditsData.data.cast.isNotEmpty()) {
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
                                        text = "Credit as Cast",
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
                                            personMovieCreditsData.data.cast,
                                            itemContent = { itemMovie ->
                                                MovieView(
                                                    itemMovie = itemMovie,
                                                    navController = navController
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                            if (personMovieCreditsData.data.crew.isNotEmpty()) {
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
                                        text = "Credit as Crew",
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
                                            personMovieCreditsData.data.crew,
                                            itemContent = { itemMovie ->
                                                MovieView(
                                                    itemMovie = itemMovie,
                                                    navController = navController
                                                )
                                            }
                                        )
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
fun PersonalInfo(title: String, info: String) {
    Column(modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)) {
        Text(
            text = title,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                color = Color(0xFF212121),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            text = info,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}