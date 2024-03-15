package com.fazalulabid.samespacemusic.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazalulabid.samespacemusic.core.util.Resource
import com.fazalulabid.samespacemusic.domain.usecase.GetAllMusicTracksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllMusicTracksUseCase: GetAllMusicTracksUseCase
) : ViewModel() {

    private val _musicTrackState = mutableStateOf(MusicTracksState())
    val musicTrackState: State<MusicTracksState> = _musicTrackState

    init {
        viewModelScope.launch {
            getAllMusicTracks()
        }
    }

    private suspend fun getAllMusicTracks() {
        _musicTrackState.value = _musicTrackState.value.copy(isLoading = true)
        getAllMusicTracksUseCase(Unit).collect { result ->
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
                        musicTracks = result.data ?: emptyList()
                    )
                }
            }
        }
    }
}