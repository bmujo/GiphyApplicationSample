package com.samplecode.giphyapplication.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.samplecode.giphyapplication.apiclient.GiphyApiInterface
import com.samplecode.giphyapplication.viewmodels.GifPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GifRepository @Inject constructor(private val gifApi: GiphyApiInterface){
    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 15,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GifPagingSource(gifApi, query)}
        ).liveData
}