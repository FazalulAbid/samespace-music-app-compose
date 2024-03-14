package com.fazalulabid.samespacemusic.presentation.screens.player

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.fazalulabid.samespacemusic.presentation.ui.theme.StandardScreenPadding

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerThumbnailPager(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 3 * StandardScreenPadding)
) {
    val pagerState = rememberPagerState(pageCount = { 10 })
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        contentPadding = contentPadding,
        verticalAlignment = Alignment.CenterVertically,
        pageSpacing = StandardScreenPadding,

    ) { page ->
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .background(Color.Yellow)
                .border(4.dp, Color.White)
        )
    }
}