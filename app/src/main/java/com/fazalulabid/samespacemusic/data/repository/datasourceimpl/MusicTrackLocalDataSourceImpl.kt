package com.fazalulabid.samespacemusic.data.repository.datasourceimpl

import com.fazalulabid.samespacemusic.data.db.MusicTracksDao
import com.fazalulabid.samespacemusic.data.repository.datasource.MusicTrackLocalDataSource
import com.fazalulabid.samespacemusic.domain.model.MusicTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicTrackLocalDataSourceImpl(
    private val musicTracksDao: MusicTracksDao
) : MusicTrackLocalDataSource {

    override suspend fun getAllMusicTracksFromDb(): List<MusicTrack> {
        return musicTracksDao.getAllMusicTracks()
    }

    override suspend fun saveMusicTracksToDb(musicTracks: List<MusicTrack>) {
        CoroutineScope(Dispatchers.IO).launch {
            musicTracksDao.saveMusicTracks(musicTracks)
        }
    }

    override suspend fun clearAllMusicTracks() {
        CoroutineScope(Dispatchers.IO).launch {
            musicTracksDao.deleteAllMusicTracks()
        }
    }
}