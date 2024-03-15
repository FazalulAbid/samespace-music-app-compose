package com.fazalulabid.samespacemusic.data.remote.dto

import com.fazalulabid.samespacemusic.domain.model.MusicTrack
import com.google.gson.annotations.SerializedName

data class MusicTrackDto(
    @SerializedName("id") val id: Int,
    @SerializedName("status") val status: String,
    @SerializedName("user_created") val userCreated: String,
    @SerializedName("date_created") val dateCreated: String,
    @SerializedName("user_updated") val userUpdated: String,
    @SerializedName("date_updated") val dateUpdated: String,
    @SerializedName("name") val name: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("accent") val accent: String,
    @SerializedName("cover") val cover: String,
    @SerializedName("top_track") val topTrack: Boolean,
    @SerializedName("url") val url: String
)

fun Iterable<MusicTrackDto>.toMusicTrackList(): List<MusicTrack> {
    return this.map {
        MusicTrack(
            id = it.id,
            status = it.status,
            userCreated = it.userCreated,
            dateCreated = it.dateCreated,
            userUpdated = it.userUpdated,
            dateUpdated = it.dateUpdated,
            name = it.name,
            artist = it.artist,
            accent = it.accent,
            cover = it.cover,
            topTrack = it.topTrack,
            url = it.url
        )
    }
}
