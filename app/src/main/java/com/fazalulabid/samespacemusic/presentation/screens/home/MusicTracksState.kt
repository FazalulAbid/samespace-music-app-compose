package com.fazalulabid.samespacemusic.presentation.screens.home

import com.fazalulabid.samespacemusic.domain.model.MusicTrack
import com.fazalulabid.samespacemusic.domain.model.MusicTrackThumbnail
import com.fazalulabid.samespacemusic.presentation.util.UiText

data class MusicTracksState(
    val isLoading: Boolean = false,
    val musicTracks: List<MusicTrack> = emptyList(),
    val musicTrackThumbnails: List<MusicTrackThumbnail> = emptyList(),
    val error: UiText? = null,
    val currentlyPlayingTrackIndex: Long? = null,
)
