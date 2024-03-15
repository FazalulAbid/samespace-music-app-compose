package com.fazalulabid.samespacemusic.data.remote.dto

import com.fazalulabid.samespacemusic.domain.model.MusicTrack
import com.google.gson.annotations.SerializedName

data class MusicTracksDto(
    @SerializedName("data")
    val musicTracks: List<MusicTrackDto>
)