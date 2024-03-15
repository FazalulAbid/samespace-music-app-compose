package com.fazalulabid.samespacemusic.domain.model

import com.fazalulabid.samespacemusic.core.util.Constants

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
