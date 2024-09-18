package com.arshia.moviedatademo.data.datasource.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arshia.moviedatademo.data.datasource.remote.DataApiService
import com.arshia.moviedatademo.data.model.ItemMovie
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchedMoviePagingSource @Inject constructor(
    private val apiService: DataApiService,
    private val searchedKey: String
) :
    PagingSource<Int, ItemMovie>() {
    override fun getRefreshKey(state: PagingState<Int, ItemMovie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemMovie> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = apiService.searchedMovieList(nextPageNumber, searchedKey)

            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (response.results.isNotEmpty()) response.page + 1 else null
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}