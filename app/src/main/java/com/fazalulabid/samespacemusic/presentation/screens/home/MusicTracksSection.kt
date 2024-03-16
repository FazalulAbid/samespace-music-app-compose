package com.fazalulabid.samespacemusic.presentation.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.fazalulabid.samespacemusic.domain.model.MusicTrack
import com.fazalulabid.samespacemusic.presentation.components.MusicTrackList
import com.fazalulabid.samespacemusic.presentation.components.MusicTrackShimmerItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MusicTracksSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    pagerState: PagerState,
    imageLoader: ImageLoader,
    items: List<MusicTrack>,
    contentPadding: PaddingValues,
    onItemClick: (Int) -> Unit
) {
    val fadeInAnimation = fadeIn(animationSpec = TweenSpec(durationMillis = 1000))
    val fadeOutAnimation = fadeOut(animationSpec = TweenSpec(durationMillis = 500))

    AnimatedVisibility(
        visible = isLoading,
        enter = fadeInAnimation,
        exit = fadeOutAnimation
    ) {
        LazyColumn {
            items(12) {
                MusicTrackShimmerItem()
            }
        }
    }

    AnimatedVisibility(
        visible = !isLoading,
        enter = fadeInAnimation,
        exit = fadeOutAnimation
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
}