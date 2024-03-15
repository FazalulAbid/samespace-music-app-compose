package com.fazalulabid.samespacemusic.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.fazalulabid.samespacemusic.domain.model.MusicTrack
import com.fazalulabid.samespacemusic.presentation.ui.theme.PrimaryButtonHeight
import com.fazalulabid.samespacemusic.presentation.ui.theme.StandardScreenPadding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MusicTrackList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    imageLoader: ImageLoader,
    items: List<MusicTrack>,
    onItemClick: () -> Unit
) {
    LazyColumn(
        contentPadding = contentPadding
    ) {
        items(items) {
            SongItem(
                musicTrack = it,
                imageLoader = imageLoader,
                onClick = { onItemClick() }
            )
        }
    }
}