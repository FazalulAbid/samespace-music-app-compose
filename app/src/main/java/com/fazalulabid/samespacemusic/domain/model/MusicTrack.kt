package com.fazalulabid.samespacemusic.domain.model

import com.fazalulabid.samespacemusic.core.util.Constants
import com.fazalulabid.samespacemusic.data.remote.dto.MusicTrackDto

data class MusicTrack(
    val id: Int,
    val status: String,
    val userCreated: String,
    val dateCreated: String,
    val userUpdated: String,
    val dateUpdated: String,
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