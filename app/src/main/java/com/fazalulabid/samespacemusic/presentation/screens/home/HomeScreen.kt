package com.fazalulabid.samespacemusic.presentation.screens.home

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fazalulabid.samespacemusic.R
import com.fazalulabid.samespacemusic.domain.model.Song
import com.fazalulabid.samespacemusic.presentation.components.SongItem
import com.fazalulabid.samespacemusic.presentation.ui.theme.PrimaryButtonHeight
import com.fazalulabid.samespacemusic.presentation.ui.theme.SpaceExtraSmall
import com.fazalulabid.samespacemusic.presentation.ui.theme.SpaceLarge
import com.fazalulabid.samespacemusic.presentation.ui.theme.SpaceMedium
import com.fazalulabid.samespacemusic.presentation.ui.theme.SpaceSmall
import com.fazalulabid.samespacemusic.presentation.util.NoRippleTheme

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
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { index ->
            LazyColumn(
                contentPadding = PaddingValues(vertical = PrimaryButtonHeight)
            ) {
                items(20) {
                    SongItem(
                        song = dummySong,
                        onClick = {}
                    )
                }
            }
        }
        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = SpaceLarge),
                indicator = {},
                divider = {},
                containerColor = Color.Transparent
            ) {
                tabItems.forEachIndexed { index, tabItem ->
                    val dothAlpha by animateFloatAsState(
                        targetValue = if (index == selectedTabIndex) 1f else 0f, label = "",
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioHighBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = {
                            selectedTabIndex = index
                        },
                    ) {
                        Text(
                            text = tabItem.title,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = if (index == selectedTabIndex) {
                                    MaterialTheme.colorScheme.onBackground
                                } else MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Spacer(modifier = Modifier.height(SpaceExtraSmall))
                        Icon(
                            modifier = Modifier
                                .size(SpaceExtraSmall)
                                .graphicsLayer {
                                    alpha = dothAlpha
                                    translationY = (1f - dothAlpha) * 48.dp.toPx()
                                    rotationZ = 0f
                                },
                            painter = painterResource(id = R.drawable.ic_circle),
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.height(SpaceLarge))
                    }
                }
            }
        }
    }
}

data class TabItem(
    val title: String
)