package com.arshia.moviedatademo.ui.screens.movie.component

import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.arshia.moviedatademo.data.model.Genre
import com.arshia.moviedatademo.ui.theme.Thunder
import com.arshia.moviedatademo.ui.theme.LightningYellow
import com.arshia.moviedatademo.ui.theme.MulberryWood
import com.arshia.moviedatademo.ui.theme.BlackMarlin
import com.arshia.moviedatademo.ui.theme.Nugget


@Composable
fun MovieGenreTaps(
    genreList: MutableState<ArrayList<Genre>>,
    selectedGenre: Genre,
    onGenreChanged: (Genre) -> Unit
) {

    val selectedIndex = genreList.value.indexOfFirst { it == selectedGenre }

    ScrollableTabRow(
        selectedTabIndex = genreList.value.indexOfFirst { it == selectedGenre },
        divider = {},
        edgePadding = 24.dp,
        indicator = emptyTabIndicator,
        modifier = Modifier.absoluteOffset(),
        containerColor = Thunder
    ) {
        genreList.value.forEachIndexed { index, genre ->
            Tab(
                selected = selectedIndex == index,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 4.dp, vertical = 16.dp)
                    .clip(CircleShape),
                onClick = { onGenreChanged(genre) }
            ) {
                ChoiceChipItem(
                    text = genre.name,
                    selected = index == selectedIndex,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                )
            }
        }
    }

}


@Composable
private fun ChoiceChipItem(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        color = when {
            selected -> MulberryWood
            else -> BlackMarlin
        },
        contentColor = when {
            selected -> LightningYellow
            else -> Nugget
        },
        modifier = modifier
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

private val emptyTabIndicator: @Composable (List<TabPosition>) -> Unit = {}