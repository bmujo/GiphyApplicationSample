package com.samplecode.giphyapplication.di

import com.google.gson.GsonBuilder
import com.samplecode.giphyapplication.apiclient.GiphyApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val gson = GsonBuilder()
            .setLenient()
            .create()

    val okHttp = OkHttpClient.Builder().addInterceptor(RequestInterceptor())

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(GiphyApiInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttp.build())
            .build()

    @Provides
    @Singleton
    fun provideGiphyApi(retrofit: Retrofit): GiphyApiInterface =
        retrofit.create(GiphyApiInterface::class.java)
}

internal class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val url = oldRequest.url().newBuilder()
            .addQueryParameter("api_key", GiphyApiInterface.API_KEY)
            .build()
        val newRequest = oldRequest.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }
}