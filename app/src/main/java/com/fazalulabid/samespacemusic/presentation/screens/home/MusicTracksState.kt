package com.fazalulabid.samespacemusic.presentation.screens.home

import com.fazalulabid.samespacemusic.domain.model.MusicTrack
import com.fazalulabid.samespacemusic.presentation.util.UiText

data class MusicTracksState(
    val isLoading: Boolean = false,
    val musicTracks: List<MusicTrack> = emptyList(),
    val error: UiText? = null
)
