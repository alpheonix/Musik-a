package com.axt.esgi.esgi4a2020.data.dto.mapper

import com.axt.esgi.esgi4a2020.data.dto.AlbumsResponseDTO
import com.axt.esgi.esgi4a2020.data.dto.TracksResponseDTO
import com.axt.esgi.esgi4a2020.data.model.Album
import com.axt.esgi.esgi4a2020.data.model.Tracks

class AlbumsResponseMapper {

    fun map(albumsResponse: AlbumsResponseDTO): List<Album> {
        val albumListDTO = albumsResponse.data

        return albumListDTO.map { albumDto ->
            Album(albumDto.id, albumDto.title,albumDto.coverSmall,albumDto.coverMedium,albumDto.releaseDate)
        }
    }
}

class TracklMapper {
    fun map(trackDetail: TracksResponseDTO): List<Tracks> {
        val trackListDTO = trackDetail.data

        return trackListDTO.map { trackDto ->
            Tracks(trackDto.id, trackDto.title,trackDto.preview,trackDto.duration)
        }
    }
}