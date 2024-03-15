package com.fazalulabid.samespacemusic.presentation.screens.home

import android.util.Log
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

    val TAG = "Hello : HomeViewModel"

    init {
        getAllMusicTracks()
    }

    private fun getAllMusicTracks() {
        viewModelScope.launch {
            getAllMusicTracksUseCase(Unit).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        Log.d(TAG, "getAllMusicTracks: ${result.error}")
                    }

                    is Resource.Loading -> {
                        Log.d(TAG, "getAllMusicTracks: Loading")
                    }

                    is Resource.Success -> {
                        Log.d(TAG, "getAllMusicTracks: ${result.data}")
                    }
                }
            }
        }
    }
}