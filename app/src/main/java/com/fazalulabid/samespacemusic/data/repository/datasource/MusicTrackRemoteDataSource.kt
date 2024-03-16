package com.fazalulabid.samespacemusic.data.repository.datasource

import com.fazalulabid.samespacemusic.data.remote.dto.MusicTracksDto
import retrofit2.Response

interface MusicTrackRemoteDataSource {
    suspend fun getAllMusicTracks(): Response<MusicTracksDto>
}