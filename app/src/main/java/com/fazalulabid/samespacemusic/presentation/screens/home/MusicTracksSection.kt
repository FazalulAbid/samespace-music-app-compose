package com.fazalulabid.samespacemusic.presentation.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import coil.ImageLoader
import com.fazalulabid.samespacemusic.domain.model.MusicTrack
import com.fazalulabid.samespacemusic.presentation.components.MusicTrackList
import com.fazalulabid.samespacemusic.presentation.components.MusicTrackShimmerItem
import com.fazalulabid.samespacemusic.presentation.ui.theme.SizeSmall8
import com.fazalulabid.samespacemusic.presentation.util.UiText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MusicTracksSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    pagerState: PagerState,
    imageLoader: ImageLoader,
    items: List<MusicTrack>,
    contentPadding: PaddingValues,
    error: UiText? = null,
    onErrorRetry: () -> Unit,
    onItemClick: (Int) -> Unit
) {

    AnimatedVisibility(
        visible = isLoading,
        enter = fadeIn(animationSpec = tween(100)),
        exit = fadeOut()
    ) {
        LazyColumn {
            items(12) {
                MusicTrackShimmerItem()
            }
        }
    }

    AnimatedVisibility(
        visible = !isLoading,
        enter = fadeIn(animationSpec = TweenSpec(1000)),
        exit = fadeOut(animationSpec = TweenSpec(500))
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier.fillMaxSize()
        ) { page ->
            when (page) {
                FOR_YOU_PAGE -> {
                    MusicTrackList(
                        imageLoader = imageLoader,
                        items = items,
                        contentPadding = contentPadding,
                        onItemClick = onItemClick
                    )
                }

                TOP_TRACKS_PAGE -> {
                    MusicTrackList(
                        imageLoader = imageLoader,
                        items = items,
                        contentPadding = contentPadding,
                        isTopTrackList = true,
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }

    AnimatedVisibility(
        visible = error != null,
        enter = fadeIn(animationSpec = TweenSpec(1000)),
        exit = fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Oops",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Spacer(modifier = Modifier.height(SizeSmall8))
            Text(
                text = "Something went wrong, try again!",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Spacer(modifier = Modifier.height(SizeSmall8))
            Button(
                onClick = onErrorRetry
            ) {
                Text(
                    text = "Retry",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}