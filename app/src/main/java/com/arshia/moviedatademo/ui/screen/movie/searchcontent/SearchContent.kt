package com.arshia.moviedatademo.ui.screens.movie.searchcontent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.arshia.moviedatademo.R
import com.arshia.moviedatademo.data.base.BaseApiURL
import com.arshia.moviedatademo.data.model.ItemMovie
import com.arshia.moviedatademo.navigation.ScreenObject
import com.arshia.moviedatademo.ui.theme.LightningYellow
import com.arshia.moviedatademo.ui.theme.SearchContentBrush
import com.arshia.moviedatademo.ui.theme.BlackMarlin
import com.arshia.moviedatademo.utils.items
import com.arshia.moviedatademo.utils.roundTo
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update

@Composable
fun MovieSearchContent(
    navController: NavController,
    searchedKey: String,
    resetSearchBar: () -> Unit
) {
    val viewModel = hiltViewModel<SearchContentViewModel>()
    viewModel.searchedKey.update { searchedKey }
    val searchedMovieList: Flow<PagingData<ItemMovie>> = viewModel.searchedMovieList
    val searchedMovieItemList: LazyPagingItems<ItemMovie> =
        searchedMovieList.collectAsLazyPagingItems()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(0.dp, 350.dp)
            .clip(RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp))
            .background(SearchContentBrush),
        state = LazyListState(0)
    ) {
        items(searchedMovieItemList) { item ->
            item?.let {
                SearchedMovieItemView(navController, item, resetSearchBar)
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SearchedMovieItemView(
    navController: NavController,
    item: ItemMovie?,
    resetSearchBar: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(BlackMarlin)
            .clickable {
                resetSearchBar()
                navController.navigate(ScreenObject.MovieDetail.route.plus("/${item?.id}"))
            }
    ) {
        if (item != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
            ) {
                GlideImage(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(120.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    model = BaseApiURL.BASE_IMAGE_API_URL.plus(item.posterPath),
                    contentDescription = item.title,
                    failure = placeholder(R.drawable.no_image),
                ) {
                    it.transform(CenterInside(), RoundedCorners(32))
                }
                Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                    Text(
                        text = item.title,
                        modifier = Modifier.padding(
                            start = 8.dp,
                            top = 4.dp
                        ),
                        fontWeight = FontWeight.SemiBold,
                        color = LightningYellow
                    )
                    Text(
                        text = item.releaseDate,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp),
                        color = LightningYellow
                    )
                    Text(
                        text = "${stringResource(R.string.rating_search)} ${
                            item.voteAverage.roundTo(
                                1
                            )
                        }",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 8.dp),
                        color = LightningYellow
                    )
                }
            }
        }
    }
}


