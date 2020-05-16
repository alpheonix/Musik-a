package com.axt.esgi.esgi4a2020.data.api

import com.axt.esgi.esgi4a2020.data.dto.AlbumDetailDTO
import com.axt.esgi.esgi4a2020.data.dto.AlbumsResponseDTO
import com.axt.esgi.esgi4a2020.data.dto.TracksResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerService {
    @GET("?method=flickr.people.getPublicPhotos")
    fun getPhotos(@Query("user_id") userId: String): Call<AlbumsResponseDTO>
    @GET("user/2529/albums")
    fun getAlbum(): Call<AlbumsResponseDTO>


    @GET("?method=flickr.photos.getInfo")
    fun getPhotoInfo(@Query("photo_id") photoId: String): Call<AlbumDetailDTO>

    @GET("album/{albumId}/tracks")
    fun getTracks(@Path("albumId") photoId: Int): Call<TracksResponseDTO>

}