package com.fazalulabid.samespacemusic.data.repository

import com.fazalulabid.samespacemusic.core.util.CacheManager
import com.fazalulabid.samespacemusic.data.datastore.DataStoreHelper
import com.fazalulabid.samespacemusic.data.remote.dto.toMusicTrackList
import com.fazalulabid.samespacemusic.data.repository.datasource.MusicTrackLocalDataSource
import com.fazalulabid.samespacemusic.data.repository.datasource.MusicTrackRemoteDataSource
import com.fazalulabid.samespacemusic.domain.model.MusicTrack
import com.fazalulabid.samespacemusic.domain.repository.MusicTrackRepository
import kotlinx.coroutines.delay

class MusicTrackRepositoryImpl(
    private val remoteDataSource: MusicTrackRemoteDataSource,
    private val localDataSource: MusicTrackLocalDataSource,
    private val dataStoreHelper: DataStoreHelper,
    private val cacheManager: CacheManager
) : MusicTrackRepository {

    override suspend fun getAllSongs(needToFetchFromApi: Boolean): List<MusicTrack> {
        if (needToFetchFromApi) {
            localDataSource.clearAllMusicTracks()
        }
        return getMusicTracksFromDb()
    }

    override suspend fun getLastFetchedTime(): Long =
        dataStoreHelper.getLastFetchedTime() ?: 0

    private suspend fun getMusicTracksFromDb(): List<MusicTrack> {
        lateinit var musicTracks: List<MusicTrack>
        musicTracks = localDataSource.getAllMusicTracksFromDb()
        if (musicTracks.isNotEmpty() && cacheManager.isDataValid()) {
            return musicTracks
        } else {
            musicTracks = getMusicTracksFromApi()
            localDataSource.saveMusicTracksToDb(musicTracks)
        }
        return musicTracks
    }

    private suspend fun getMusicTracksFromApi(): List<MusicTrack> {
        lateinit var musicTracks: List<MusicTrack>
        delay(3000)
        val response = remoteDataSource.getAllMusicTracks()
        val body = response.body()
        if (body != null) {
            musicTracks = body.musicTracks.toMusicTrackList()
            dataStoreHelper.setLastFetchedTime(System.currentTimeMillis())
        }
        return musicTracks
    }
}