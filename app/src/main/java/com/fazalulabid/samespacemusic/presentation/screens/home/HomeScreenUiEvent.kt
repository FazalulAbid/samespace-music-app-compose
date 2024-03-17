package com.fazalulabid.samespacemusic.presentation.screens.home

sealed class HomeScreenUiEvent {
    data object PlaySelectedMusic : HomeScreenUiEvent()
}