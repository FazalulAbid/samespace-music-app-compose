package com.fazalulabid.samespacemusic.presentation.screens.home

sealed class MusicTrackEvent {
    data class SelectMusicTrack(val index: Long) : MusicTrackEvent()
    data class GetMusicTracks(val isRefresh: Boolean = false) : MusicTrackEvent()
    data class SetCurrentPosition(val currentPosition: Long) : MusicTrackEvent()
    data class SetSliderPosition(val sliderPosition: Long) : MusicTrackEvent()
    data class SetTotalDuration(val totalDuration: Long) : MusicTrackEvent()
}