package com.arshia.moviedatademo.data.datasource.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arshia.moviedatademo.data.datasource.remote.DataApiService
import com.arshia.moviedatademo.data.model.ItemMovie
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpcomingMoviePagingSource @Inject constructor(private val apiService: DataApiService, private var genreId:Int?) :
    PagingSource<Int, ItemMovie>() {
    override fun getRefreshKey(state: PagingState<Int, ItemMovie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemMovie> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = apiService.upcomingMovieList(nextPageNumber)
            val data = if (genreId != null) {
                response.results.filter {itemMovie ->
                    itemMovie.genreIds.contains(genreId)
                }
            } else {
                response.results
            }
            LoadResult.Page(
                data = data,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (response.results.isNotEmpty()) response.page + 1 else null
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }
}