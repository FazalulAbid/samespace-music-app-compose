package com.fazalulabid.samespacemusic.presentation.screens.home

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import coil.ImageLoader
import com.fazalulabid.samespacemusic.core.util.Constants.homeScreenTabs
import com.fazalulabid.samespacemusic.presentation.components.GradientBox
import com.fazalulabid.samespacemusic.presentation.components.HomeTabRow
import com.fazalulabid.samespacemusic.presentation.screens.player.PlayerCollapsedContent
import com.fazalulabid.samespacemusic.presentation.screens.player.PlayerExpandedContent
import com.fazalulabid.samespacemusic.presentation.ui.theme.StandardScreenPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    imageLoader: ImageLoader,
    player: ExoPlayer,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val musicTrackState = viewModel.musicTrackState.value
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState {
        homeScreenTabs.size
    }
    var bottomTabRowHeightInDp by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val playerSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = musicTrackState.isLoading)
    var isPlayerSheetOpen by rememberSaveable { mutableStateOf(false) }

    // Player States
    val isPlaying = remember { mutableStateOf(false) }
    val currentPosition = remember { mutableLongStateOf(0) }
    val sliderPosition = remember { mutableLongStateOf(0) }
    val totalDuration = remember { mutableLongStateOf(0) }

    LaunchedEffect(key1 = player.currentPosition, key2 = player.isPlaying) {
        delay(1000)
        currentPosition.longValue = player.currentPosition
    }

    LaunchedEffect(currentPosition.longValue) {
        sliderPosition.longValue = currentPosition.longValue
    }

    LaunchedEffect(player.duration) {
        if (player.duration > 0) {
            totalDuration.longValue = player.duration
        }
    }

    LaunchedEffect(musicTrackState.musicTracks) {
        musicTrackState.musicTracks.forEach { musicTrack ->
            player.addMediaItem(MediaItem.fromUri(musicTrack.getMediaUrl()))
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeScreenUiEvent.PlaySelectedMusic -> {
                    isPlayerSheetOpen = true
                }
            }
        }
    }

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(
            page = selectedTabIndex,
            animationSpec = tween(300)
        )
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.onEvent(MusicTrackEvent.GetMusicTracks(isRefresh = true))
            }
        ) {
            MusicTracksSection(
                pagerState = pagerState,
                imageLoader = imageLoader,
                isLoading = musicTrackState.isLoading,
                items = musicTrackState.musicTracks,
                contentPadding = PaddingValues(
                    bottom = bottomTabRowHeightInDp + StandardScreenPadding
                ),
                error = musicTrackState.error,
                onErrorRetry = {
                    viewModel.onEvent(MusicTrackEvent.GetMusicTracks())
                },
                onItemClick = { musicTrackIndex ->
                    viewModel.onEvent(MusicTrackEvent.SelectMusicTrack(musicTrackIndex.toLong()))
                }
            )
        }
        GradientBox(
            modifier = Modifier.fillMaxSize(),
            bottomGradientHeight = bottomTabRowHeightInDp,
            isCurrentlyPlaying = isPlaying.value
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .onGloballyPositioned { coordinates ->
                        bottomTabRowHeightInDp = with(density) {
                            coordinates.size.height.toDp()
                        }
                    }
            ) {
                musicTrackState.currentlyPlayingTrackIndex?.let { index ->
                    PlayerCollapsedContent(
                        currentMusicTrack = musicTrackState.musicTracks[index.toInt()],
                        imageLoader = imageLoader,
                        isPlaying = isPlaying.value,
                        onClick = {
                            isPlayerSheetOpen = true
                        },
                        onActionClick = {
                            if (isPlaying.value) player.pause()
                            else player.play()
                            isPlaying.value = player.isPlaying
                        }
                    )
                }
                HomeTabRow(
                    modifier = Modifier.background(
                        if (musicTrackState.currentlyPlayingTrackIndex != null) {
                            MaterialTheme.colorScheme.background
                        } else Color.Transparent
                    ),
                    selectedTabIndex = selectedTabIndex,
                    tabItems = homeScreenTabs,
                    onTabClick = { index ->
                        selectedTabIndex = index
                    }
                )
            }
        }


        if (isPlayerSheetOpen) {
            ModalBottomSheet(
                onDismissRequest = {
                    isPlayerSheetOpen = false
                },
                sheetState = playerSheetState,
                shape = RectangleShape,
                dragHandle = {},
                windowInsets = WindowInsets(0.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    musicTrackState.currentlyPlayingTrackIndex?.let { index ->
                        PlayerExpandedContent(
                            currentlyPlayingMusicTrack = musicTrackState.musicTracks[index.toInt()],
                            musicTrackThumbnailList = musicTrackState.musicTrackThumbnails,
                            imageLoader = imageLoader,
                            currentlyPlayingMusicTrackIndex = index.toInt(),
                            totalDuration = totalDuration.longValue,
                            isPlaying = isPlaying.value,
                            onThumbnailPagerChanged = { musicTrackIndex ->
                                viewModel.onEvent(MusicTrackEvent.SelectMusicTrack(musicTrackIndex.toLong()))
                            },
                            onSliderValueChange = {
                                sliderPosition.longValue = it.toLong()
                            },
                            onSliderValueChangeFinished = {
                                currentPosition.longValue = sliderPosition.longValue
                                player.seekTo(sliderPosition.longValue)
                            },
                            currentPosition = currentPosition.longValue,
                            sliderPosition = sliderPosition.longValue.toFloat()
                        )
                    }
                }
            }
        }
    }
}

const val FOR_YOU_PAGE = 0
const val TOP_TRACKS_PAGE = 1