package com.fazalulabid.samespacemusic.presentation.screens.home

sealed class MusicTrackEvent {
    data class SelectMusicTrackAndPlay(val index: Long) : MusicTrackEvent()
    data class SelectMusicTrack(val index: Long) : MusicTrackEvent()
    data object SelectNextMusicTrack : MusicTrackEvent()
    data object SelectPreviousMusicTrack : MusicTrackEvent()
    data class GetMusicTracks(val isRefresh: Boolean = false) : MusicTrackEvent()
}