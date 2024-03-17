package com.fazalulabid.samespacemusic.presentation.screens.player

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.util.lerp
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.fazalulabid.samespacemusic.domain.model.MusicTrackThumbnail
import com.fazalulabid.samespacemusic.presentation.ui.theme.StandardScreenPadding
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerThumbnailPager(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 3 * StandardScreenPadding),
    items: List<MusicTrackThumbnail>,
    imageLoader: ImageLoader,
    currentPlayingMusicTrackIndex: Int,
    thumbnailShape: Shape = MaterialTheme.shapes.extraSmall,
    cardElevation: Dp = 0.dp,
    onThumbnailPagerChanged: (Int) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { items.size })

    LaunchedEffect(Unit) {
        pagerState.scrollToPage(currentPlayingMusicTrackIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        onThumbnailPagerChanged(pagerState.currentPage)
    }

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        HorizontalPager(
            modifier = modifier,
            state = pagerState,
            contentPadding = contentPadding,
            verticalAlignment = Alignment.CenterVertically,
            pageSpacing = StandardScreenPadding
        ) { page ->
            val musicTrackThumbnail = items[page]
            val pageOffset = (pagerState.currentPage - page) +
                    pagerState.currentPageOffsetFraction
            val scaleFactor = 0.85f + 0.15f * (1 - pageOffset.absoluteValue)

            Card(
                modifier = Modifier
                    .aspectRatio(1f)
                    .graphicsLayer {
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    },
                shape = thumbnailShape,
                elevation = CardDefaults.cardElevation(cardElevation)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = rememberAsyncImagePainter(
                        model = musicTrackThumbnail.thumbnailUrl,
                        imageLoader = imageLoader
                    ),
                    contentDescription = musicTrackThumbnail.contentDescription,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}