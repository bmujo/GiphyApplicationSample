package com.samplecode.giphyapplication.viewmodels

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.samplecode.giphyapplication.apiclient.GiphyApiInterface
import com.samplecode.giphyapplication.models.Gif
import com.samplecode.giphyapplication.models.GifData
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class GifPagingSource(
    private val gifApi: GiphyApiInterface,
    private val query: String
) : PagingSource<Int, Gif>() {

    override fun getRefreshKey(state: PagingState<Int, Gif>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Gif> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {

            val response: Call<GifData> = if(query.isEmpty()){
                gifApi.getTrendingGifs(params.loadSize, position)
            } else{
                gifApi.getSearchedGif(query, position, params.loadSize)
            }

            val gifData = response.awaitResponse().body()

            LoadResult.Page(
                data = gifData!!.data,
                prevKey = if(position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if(gifData.data.isEmpty()) null else position + 1
            )
        }catch (exception: IOException){
            //will be thrown if there is no internet connection
            LoadResult.Error(exception)
        }catch (exception: HttpException){
            //will be thrown if something went wrong on server
            LoadResult.Error(exception)
        }
    }
}