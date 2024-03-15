package com.fazalulabid.samespacemusic.data.repository

import android.util.Log
import com.fazalulabid.samespacemusic.data.remote.MusicTrackApiService
import com.fazalulabid.samespacemusic.data.remote.dto.toMusicTrackList
import com.fazalulabid.samespacemusic.domain.model.MusicTrack
import com.fazalulabid.samespacemusic.domain.repository.MusicTrackRepository
import javax.inject.Inject

class MusicTrackRepositoryImpl @Inject constructor(
    private val apiService: MusicTrackApiService
) : MusicTrackRepository {

    override suspend fun getAllSongs(): List<MusicTrack> {
        var musicTrackList: List<MusicTrack> = emptyList()
        apiService.getAllMusicTracks().body()?.let {
            musicTrackList = it.musicTracks.toMusicTrackList()
        }
        return musicTrackList
    }
}