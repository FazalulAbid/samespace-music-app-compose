package com.fazalulabid.samespacemusic.presentation.screens.home

sealed class HomeScreenUiEvent {
    data class PlayMusicTrack(val index: Int) : HomeScreenUiEvent()
}