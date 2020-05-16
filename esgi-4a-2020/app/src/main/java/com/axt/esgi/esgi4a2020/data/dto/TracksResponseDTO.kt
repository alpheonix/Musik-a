package com.axt.esgi.esgi4a2020.data.dto


import com.google.gson.annotations.SerializedName

data class TracksResponseDTO(@SerializedName("data") val data: List<TrackDTO>)

data class TrackDTO(

    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("preview") val preview: String,
    @SerializedName("duration") val duration: Int
)