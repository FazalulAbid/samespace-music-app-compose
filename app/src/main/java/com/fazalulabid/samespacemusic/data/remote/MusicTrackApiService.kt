package com.fazalulabid.samespacemusic.data.remote

import com.fazalulabid.samespacemusic.data.remote.dto.MusicTracksDto
import retrofit2.Response
import retrofit2.http.GET

interface MusicTrackApiService {

    @GET("items/songs")
    suspend fun getAllMusicTracks(): Response<MusicTracksDto>
}