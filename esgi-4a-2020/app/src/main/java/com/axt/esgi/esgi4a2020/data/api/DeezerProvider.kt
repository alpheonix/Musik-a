package com.axt.esgi.esgi4a2020.data.api

import android.util.Log
import com.axt.esgi.esgi4a2020.BuildConfig
import com.axt.esgi.esgi4a2020.data.dto.AlbumsResponseDTO
import com.axt.esgi.esgi4a2020.data.dto.TracksResponseDTO
import com.axt.esgi.esgi4a2020.data.dto.mapper.AlbumsResponseMapper
import com.axt.esgi.esgi4a2020.data.dto.mapper.TracklMapper
import com.axt.esgi.esgi4a2020.data.model.Album
import com.axt.esgi.esgi4a2020.data.model.Tracks
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val USER_ID = "48933662@N05"
private const val PARAM_API_KEY = "api_key"
private const val PARAM_FORMAT = "format"
private const val PARAM_NOJSON_CALLBACK = "nojsoncallback"

object DeezerProvider {
    private var service: DeezerService

    init {
        service = Retrofit.Builder().baseUrl(BuildConfig.DEEZER_API_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeezerService::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor {
                val request = it.request()
                val url = request.url
                val builder = url.newBuilder()
                val newUrl = builder.build()
                val newRequest = request.newBuilder().url(newUrl).build()

                it.proceed(newRequest)
            }.build()
    }

    fun getAlbum(listener: Listener2<List<Album>>) {
        service.getAlbum().enqueue(object : Callback<AlbumsResponseDTO> {
            override fun onFailure(call: Call<AlbumsResponseDTO>, t: Throwable) {
                listener.onError(t)
            }

            override fun onResponse(
                call: Call<AlbumsResponseDTO>,
                response: Response<AlbumsResponseDTO>
            ) {
                Log.d("test",response.body().toString())
                response.body()?.let { albumsResponseDTO ->
                    val photos = AlbumsResponseMapper().map(albumsResponseDTO)
                    listener.onSuccess(photos)
                }
            }
        })
    }

    fun getTracks(albumId: Int, listener: Listener<TracksResponseDTO>) {
        Log.d("tele",albumId.toString())
        service.getTracks(albumId).enqueue(object : Callback<TracksResponseDTO>{
            override fun onFailure(call: Call<TracksResponseDTO>, t: Throwable) {
                listener.onError(t)
            }

            override fun onResponse(
                call: Call<TracksResponseDTO>,
                response: Response<TracksResponseDTO>
            ) {
                response.body()?.let {
                    val trackDetail = TracklMapper().map(it)
                    listener.onSuccess(trackDetail)
                }
            }
        })
    }
}

interface Listener<T> {
    fun onError(t: Throwable)
    fun onSuccess(track: List<Tracks>)
}

interface Listener2<T> {
    fun onError(t: Throwable)
    fun onSuccess(track: List<Album>)
}