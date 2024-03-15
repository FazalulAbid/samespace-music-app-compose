package com.fazalulabid.samespacemusic.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fazalulabid.samespacemusic.core.util.Constants

@Entity(tableName = "music_tracks")
data class MusicTrack(
    @PrimaryKey
    val id: Int,
    val name: String,
    val artist: String,
    val accent: String,
    val cover: String,
    val topTrack: Boolean,
    val url: String
) {

    fun getCoverImageUrl() = Constants.BASE_IMAGE_URL + cover
}

fun Iterable<MusicTrack>?.toMusicTrackThumbnails(): List<MusicTrackThumbnail>? {
    return this?.map {
        MusicTrackThumbnail(
            musicTrackId = it.id,
            thumbnailUrl = it.getCoverImageUrl(),
            contentDescription = "${it.name} thumbnail"
        )
    }
}