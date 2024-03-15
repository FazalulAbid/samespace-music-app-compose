package com.fazalulabid.samespacemusic.presentation.screens.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.fazalulabid.samespacemusic.domain.model.MusicTrack
import com.fazalulabid.samespacemusic.presentation.components.GradientBox
import com.fazalulabid.samespacemusic.presentation.components.HomeTabRow
import com.fazalulabid.samespacemusic.presentation.components.MusicTrackList
import com.fazalulabid.samespacemusic.presentation.components.SongItem
import com.fazalulabid.samespacemusic.presentation.screens.player.PlayerCollapsedContent
import com.fazalulabid.samespacemusic.presentation.screens.player.PlayerExpandedContent
import com.fazalulabid.samespacemusic.presentation.ui.theme.CollapsedPlayerSize
import com.fazalulabid.samespacemusic.presentation.ui.theme.PrimaryButtonHeight
import com.fazalulabid.samespacemusic.presentation.ui.theme.SizeSmall8
import com.fazalulabid.samespacemusic.presentation.ui.theme.StandardScreenPadding
import com.fazalulabid.samespacemusic.presentation.util.TabItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    imageLoader: ImageLoader,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val musicTrackState = viewModel.musicTrackState.value
    val tabItems = listOf(
        TabItem("For You"),
        TabItem("Top Tracks")
    )
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState {
        tabItems.size
    }
    var bottomTabRowHeightInDp by remember { mutableStateOf(0.dp) }

    val density = LocalDensity.current
    val playerSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var isPlayerSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()

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
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            when (page) {
                FOR_YOU_PAGE -> {
                    MusicTrackList(
                        imageLoader = imageLoader,
                        items = musicTrackState.musicTracks,
                        contentPadding = PaddingValues(
                            bottom = bottomTabRowHeightInDp + StandardScreenPadding
                        ),
                        onItemClick = {
                            coroutineScope.launch {
                                isPlayerSheetOpen = true
                            }
                        }
                    )
                }

                TOP_TRACKS_PAGE -> {
                    MusicTrackList(
                        imageLoader = imageLoader,
                        items = musicTrackState.musicTracks,
                        contentPadding = PaddingValues(
                            bottom = bottomTabRowHeightInDp + StandardScreenPadding
                        ),
                        isTopTrackList = true,
                        onItemClick = {
                            coroutineScope.launch {
                                isPlayerSheetOpen = true
                            }
                        }
                    )
                }
            }
        }
        GradientBox(
            modifier = Modifier.fillMaxSize(),
            bottomGradientHeight = bottomTabRowHeightInDp,
            isCurrentlyPlaying = musicTrackState.currentlyPlaying != null
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
                musicTrackState.currentlyPlaying?.let { musicTrack ->
                    PlayerCollapsedContent(
                        currentMusicTrack = musicTrack,
                        imageLoader = imageLoader,
                        onClick = {
                            isPlayerSheetOpen = true
                        },
                        onActionClick = {

                        }
                    )
                }
                HomeTabRow(
                    modifier = Modifier.background(
                        if (musicTrackState.currentlyPlaying != null) {
                            MaterialTheme.colorScheme.background
                        } else Color.Transparent
                    ),
                    selectedTabIndex = selectedTabIndex,
                    tabItems = tabItems,
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
                    musicTrackState.currentlyPlaying?.let { currentlyPlayingMusicTrack ->
                        PlayerExpandedContent(
                            currentlyPlayingMusicTrack = currentlyPlayingMusicTrack
                        )
                    }
                }
            }
        }
    }
}

const val FOR_YOU_PAGE = 0
const val TOP_TRACKS_PAGE = 1