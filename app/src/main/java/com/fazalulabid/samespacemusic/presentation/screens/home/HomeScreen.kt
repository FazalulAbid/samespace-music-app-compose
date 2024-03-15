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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import com.fazalulabid.samespacemusic.domain.model.Song
import com.fazalulabid.samespacemusic.presentation.components.GradientBox
import com.fazalulabid.samespacemusic.presentation.components.HomeTabRow
import com.fazalulabid.samespacemusic.presentation.components.SongItem
import com.fazalulabid.samespacemusic.presentation.screens.player.PlayerExpandedContent
import com.fazalulabid.samespacemusic.presentation.ui.theme.PrimaryButtonHeight
import com.fazalulabid.samespacemusic.presentation.ui.theme.StandardScreenPadding
import com.fazalulabid.samespacemusic.presentation.util.TabItem
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel()
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
            animationSpec = tween(600)
        )
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
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
        ) {
            LazyColumn(
                contentPadding = PaddingValues(
                    top = PrimaryButtonHeight,
                    bottom = bottomTabRowHeightInDp + StandardScreenPadding
                )
            ) {
                items(20) {
                    SongItem(
                        song = dummySong,
                        onClick = {
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
            bottomGradientHeight = 2 * bottomTabRowHeightInDp
        )
        HomeTabRow(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .onGloballyPositioned { coordinates ->
                    bottomTabRowHeightInDp = with(density) {
                        coordinates.size.height.toDp()
                    }
                },
            selectedTabIndex = selectedTabIndex,
            tabItems = tabItems,
            onTabClick = { index ->
                selectedTabIndex = index
            }
        )

        if (isPlayerSheetOpen) {
            ModalBottomSheet(
                onDismissRequest = {
                    isPlayerSheetOpen = false
                },
                sheetState = playerSheetState,
                shape = RectangleShape,
                dragHandle = {},
                windowInsets = WindowInsets(0.dp),

                ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    PlayerExpandedContent()
                }
            }
        }
    }
}
