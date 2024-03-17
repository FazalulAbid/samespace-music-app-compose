package com.fazalulabid.samespacemusic.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazalulabid.samespacemusic.core.util.Resource
import com.fazalulabid.samespacemusic.domain.model.toMusicTrackThumbnails
import com.fazalulabid.samespacemusic.domain.usecase.GetAllMusicTracksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllMusicTracksUseCase: GetAllMusicTracksUseCase
) : ViewModel() {

    private val _musicTrackState = mutableStateOf(MusicTracksState())
    val musicTrackState: State<MusicTracksState> = _musicTrackState

    private val _eventFlow = MutableSharedFlow<HomeScreenUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            getAllMusicTracks()
        }
    }

    fun onEvent(event: MusicTrackEvent) {
        when (event) {
            is MusicTrackEvent.SelectMusicTrackAndPlay -> {
                viewModelScope.launch {
                    selectMusicTrack(event.index)
                    _eventFlow.emit(HomeScreenUiEvent.PlayMusicTrack(index = event.index.toInt()))
                }
            }

            is MusicTrackEvent.SelectMusicTrack -> {
                selectMusicTrack(event.index)
            }

            is MusicTrackEvent.GetMusicTracks -> {
                viewModelScope.launch {
                    getAllMusicTracks(needToFetchFromApi = event.isRefresh)
                }
            }

            MusicTrackEvent.SelectNextMusicTrack -> {
                viewModelScope.launch {
                    setNextMusicTrackIndex()
                    _eventFlow.emit(
                        HomeScreenUiEvent.PlayMusicTrack(
                            _musicTrackState.value.currentlyPlayingTrackIndex?.toInt() ?: 0
                        )
                    )
                }
            }

            MusicTrackEvent.SelectPreviousMusicTrack -> {
                viewModelScope.launch {
                    setPreviousMusicTrackIndex()
                    _eventFlow.emit(
                        HomeScreenUiEvent.PlayMusicTrack(
                            _musicTrackState.value.currentlyPlayingTrackIndex?.toInt() ?: 0
                        )
                    )
                }
            }
        }
    }

    private fun selectMusicTrack(musicTrackIndex: Long) {
        _musicTrackState.value = musicTrackState.value.copy(
            currentlyPlayingTrackIndex = musicTrackIndex
        )
    }

    private suspend fun getAllMusicTracks(needToFetchFromApi: Boolean = false) {
        _musicTrackState.value = _musicTrackState.value.copy(isLoading = true)
        getAllMusicTracksUseCase(
            GetAllMusicTracksUseCase.Params(needToFetchFromApi)
        ).collect { result ->
            when (result) {
                is Resource.Error -> {
                    _musicTrackState.value = MusicTracksState(
                        error = result.uiText
                    )
                }

                is Resource.Loading -> {
                    _musicTrackState.value = MusicTracksState(isLoading = true)
                }

                is Resource.Success -> {
                    _musicTrackState.value = MusicTracksState(
                        musicTracks = result.data ?: emptyList(),
                        musicTrackThumbnails = result.data.toMusicTrackThumbnails() ?: emptyList()
                    )
                }
            }
        }
    }

    private fun setNextMusicTrackIndex() {
        _musicTrackState.value = musicTrackState.value.copy(
            currentlyPlayingTrackIndex = if (_musicTrackState.value.currentlyPlayingTrackIndex?.toInt() ==
                musicTrackState.value.musicTracks.count().minus(1)
            ) 0 else _musicTrackState.value.currentlyPlayingTrackIndex?.plus(1)
        )
    }

    private fun setPreviousMusicTrackIndex() {
        _musicTrackState.value = musicTrackState.value.copy(
            currentlyPlayingTrackIndex = if (_musicTrackState.value.currentlyPlayingTrackIndex?.toInt() == 0
            ) _musicTrackState.value.musicTracks.count().minus(1).toLong()
            else _musicTrackState.value.currentlyPlayingTrackIndex?.minus(1)
        )
    }
}