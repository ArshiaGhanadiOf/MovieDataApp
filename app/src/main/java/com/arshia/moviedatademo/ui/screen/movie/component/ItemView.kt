package com.arshia.moviedatademo.ui.screens.movie.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.arshia.moviedatademo.R
import com.arshia.moviedatademo.data.base.BaseApiURL
import com.arshia.moviedatademo.data.model.ItemMovie
import com.arshia.moviedatademo.navigation.ScreenObject
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItemView(
    navController: NavController,
    item: ItemMovie?,
    resetSearchBar: () -> Unit
) {
    Column(modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 15.dp, bottom = 15.dp)) {
        if (item != null) {

            GlideImage(
                modifier = Modifier
                    .size(300.dp)
                    .clickable {
                        resetSearchBar()
                        navController.navigate(ScreenObject.MovieDetail.route.plus("/${item.id}"))
                    },
                model = BaseApiURL.BASE_IMAGE_API_URL.plus(item.posterPath),
                contentDescription = item.title,
                failure = placeholder(R.drawable.no_image)
            ) {
                it.transform(CenterInside(), RoundedCorners(32))
            }

        }
    }
}


fun <T : Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyGridItemScope.(value: T?) -> Unit
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index])
    }
}