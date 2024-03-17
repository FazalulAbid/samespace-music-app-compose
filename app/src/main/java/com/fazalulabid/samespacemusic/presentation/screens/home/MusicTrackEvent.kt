package com.fazalulabid.samespacemusic.presentation.screens.home

sealed class MusicTrackEvent {
    data class SelectMusicTrackAndPlay(val index: Long) : MusicTrackEvent()
    data class SelectMusicTrack(val index: Long) : MusicTrackEvent()

    data class GetMusicTracks(val isRefresh: Boolean = false) : MusicTrackEvent()
}