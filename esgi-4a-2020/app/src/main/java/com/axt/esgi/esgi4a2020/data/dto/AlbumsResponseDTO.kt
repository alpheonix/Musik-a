package com.axt.esgi.esgi4a2020.data.dto

import com.google.gson.annotations.SerializedName

data class AlbumsResponseDTO(@SerializedName("data") val data: List<PhotoDTO>)

data class PhotoDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("cover_small") val coverSmall: String,
    @SerializedName("cover_medium") val coverMedium: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("title") val title: String
)