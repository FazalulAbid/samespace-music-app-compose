package com.fazalulabid.samespacemusic.presentation.screens.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.fazalulabid.samespacemusic.domain.model.Song
import com.fazalulabid.samespacemusic.presentation.util.TabItem
import com.fazalulabid.samespacemusic.presentation.components.GradientBox
import com.fazalulabid.samespacemusic.presentation.components.HomeTabRow
import com.fazalulabid.samespacemusic.presentation.components.SongItem
import com.fazalulabid.samespacemusic.presentation.ui.theme.PrimaryButtonHeight
import com.fazalulabid.samespacemusic.presentation.ui.theme.SpaceMedium

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues
) {

    val dummySong = Song(
        id = 1,
        status = "published",
        sort = null,
        userCreated = "2085be13-8079-40a6-8a39-c3b9180f9a0a",
        dateCreated = "2023-08-10T06:10:57.746Z",
        userUpdated = "2085be13-8079-40a6-8a39-c3b9180f9a0a",
        dateUpdated = "2023-08-10T07:19:48.547Z",
        name = "Colors",
        artist = "William King",
        accent = "#331E00",
        cover = "4f718272-6b0e-42ee-92d0-805b783cb471",
        topTrack = true,
        url = "https://pub-172b4845a7e24a16956308706aaf24c2.r2.dev/august-145937.mp3"
    )

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
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(
            page = selectedTabIndex,
            animationSpec = tween(600)
        )
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }
    val currentLocalDensity = LocalDensity.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                contentPadding = PaddingValues(
                    top = PrimaryButtonHeight,
                    bottom = bottomTabRowHeightInDp + SpaceMedium
                )
            ) {
                items(20) {
                    SongItem(
                        song = dummySong,
                        onClick = {}
                    )
                }
            }
        }
        GradientBox(
            modifier = Modifier.fillMaxSize(),
            bottomGradientHeight = 2 * bottomTabRowHeightInDp
        )
        HomeTabRow(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .onGloballyPositioned { coordinates ->
                    bottomTabRowHeightInDp = with(currentLocalDensity) {
                        coordinates.size.height.toDp()
                    }
                },
            selectedTabIndex = selectedTabIndex,
            tabItems = tabItems,
            onTabClick = { index ->
                selectedTabIndex = index
            }
        )
    }
}