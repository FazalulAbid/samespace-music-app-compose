package com.fazalulabid.samespacemusic.presentation.screens.home

import com.fazalulabid.samespacemusic.domain.model.MusicTrack

sealed class HomeScreenUiEvent {
    data class OpenPlayerBottomSheet(val currentPlayingMusicTrack: MusicTrack) : HomeScreenUiEvent()
}