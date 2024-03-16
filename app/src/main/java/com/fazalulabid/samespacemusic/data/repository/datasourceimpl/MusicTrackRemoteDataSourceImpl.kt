package com.fazalulabid.samespacemusic.data.repository.datasourceimpl

import com.fazalulabid.samespacemusic.data.remote.MusicTrackApiService
import com.fazalulabid.samespacemusic.data.remote.dto.MusicTracksDto
import com.fazalulabid.samespacemusic.data.repository.datasource.MusicTrackRemoteDataSource
import retrofit2.Response

class MusicTrackRemoteDataSourceImpl(
    private val apiService: MusicTrackApiService
) : MusicTrackRemoteDataSource {

    override suspend fun getAllMusicTracks(): Response<MusicTracksDto> =
        apiService.getAllMusicTracks()
}