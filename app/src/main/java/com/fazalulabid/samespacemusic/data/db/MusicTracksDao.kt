package com.fazalulabid.samespacemusic.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fazalulabid.samespacemusic.domain.model.MusicTrack

@Dao
interface MusicTracksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMusicTracks(musicTracks: List<MusicTrack>)

    @Query("DELETE FROM music_tracks")
    suspend fun deleteAllMusicTracks()

    @Query("SELECT * FROM music_tracks")
    suspend fun getAllMusicTracks(): List<MusicTrack>
}