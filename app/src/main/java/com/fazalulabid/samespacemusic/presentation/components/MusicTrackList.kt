package com.fazalulabid.samespacemusic.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.fazalulabid.samespacemusic.domain.model.MusicTrack

@Composable
fun MusicTrackList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    imageLoader: ImageLoader,
    items: List<MusicTrack>,
    isTopTrackList: Boolean = false,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = contentPadding
    ) {
        items(
            if (isTopTrackList) items.filter { it.topTrack }
            else items
        ) {
            SongItem(
                musicTrack = it,
                imageLoader = imageLoader,
                onClick = { onItemClick(it.id) }
            )
        }
    }
}