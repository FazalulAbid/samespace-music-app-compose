package com.fazalulabid.samespacemusic.domain.repository

import com.fazalulabid.samespacemusic.domain.model.MusicTrack

interface MusicTrackRepository {
    suspend fun getAllSongs(needToFetchFromApi: Boolean): List<MusicTrack>
    suspend fun getLastFetchedTime(): Long
}