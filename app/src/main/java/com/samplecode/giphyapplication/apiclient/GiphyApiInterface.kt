package com.samplecode.giphyapplication.apiclient

import com.samplecode.giphyapplication.models.GifData
import retrofit2.Call
import retrofit2.http.*

interface GiphyApiInterface {

    companion object{
        const val BASE_URL = "https://api.giphy.com/v1/gifs/"
        const val API_KEY = "API_KEY_HERE"
    }

    @GET("trending")
    fun getTrendingGifs(@Query("limit") limit: Int, @Query("offset") offset: Int) : Call<GifData>

    @GET("search")
    fun getSearchedGif(@Query("q") q: String, @Query("offset") offset: Int, @Query("limit") limit: Int) : Call<GifData>
}