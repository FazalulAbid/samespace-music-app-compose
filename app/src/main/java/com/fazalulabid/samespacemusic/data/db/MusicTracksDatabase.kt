package com.fazalulabid.samespacemusic.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fazalulabid.samespacemusic.domain.model.MusicTrack

@Database(
    entities = [MusicTrack::class],
    version = 1,
    exportSchema = false
)
abstract class MusicTracksDatabase : RoomDatabase() {

    abstract fun musicTracksDao(): MusicTracksDao

    companion object {
        const val DATABASE_NAME = "music_tracks_db"
    }
}