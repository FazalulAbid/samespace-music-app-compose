package com.fazalulabid.samespacemusic.data.repository.datasource

import com.fazalulabid.samespacemusic.domain.model.MusicTrack

interface MusicTrackLocalDataSource {
    suspend fun getAllMusicTracksFromDb(): List<MusicTrack>
    suspend fun saveMusicTracksToDb(musicTracks: List<MusicTrack>)
    suspend fun clearAllMusicTracks()
}