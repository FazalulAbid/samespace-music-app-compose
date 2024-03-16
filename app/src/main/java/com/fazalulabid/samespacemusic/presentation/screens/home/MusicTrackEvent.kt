package com.fazalulabid.samespacemusic.presentation.screens.home

sealed class MusicTrackEvent {
    data class SelectMusicTrack(val id: Int) : MusicTrackEvent()
    data class GetMusicTracks(val isRefresh: Boolean = false) : MusicTrackEvent()
}