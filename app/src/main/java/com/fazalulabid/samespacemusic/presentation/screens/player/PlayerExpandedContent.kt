package com.fazalulabid.samespacemusic.presentation.screens.player

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.ImageLoader
import com.fazalulabid.samespacemusic.R
import com.fazalulabid.samespacemusic.core.util.Constants.BACKGROUND_ANIMATION_DURATION
import com.fazalulabid.samespacemusic.domain.model.MusicTrack
import com.fazalulabid.samespacemusic.domain.model.MusicTrackThumbnail
import com.fazalulabid.samespacemusic.presentation.components.StandardIconButton
import com.fazalulabid.samespacemusic.presentation.components.TrackSeekBar
import com.fazalulabid.samespacemusic.presentation.ui.theme.PlayPauseButtonSize
import com.fazalulabid.samespacemusic.presentation.ui.theme.SizeHuge48
import com.fazalulabid.samespacemusic.presentation.ui.theme.SizeLarge24
import com.fazalulabid.samespacemusic.presentation.ui.theme.SizeMini4
import com.fazalulabid.samespacemusic.presentation.ui.theme.SizeStandard16
import com.fazalulabid.samespacemusic.presentation.ui.theme.StandardScreenPadding
import com.fazalulabid.samespacemusic.presentation.util.fromHex

@Composable
fun PlayerExpandedContent(
    modifier: Modifier = Modifier,
    currentlyPlayingMusicTrack: MusicTrack,
    currentlyPlayingMusicTrackIndex: Int,
    sliderPosition: Float,
    totalDuration: Float,
    currentPosition: Long,
    isPlaying: Boolean,
    isLoading: Boolean,
    musicTrackThumbnailList: List<MusicTrackThumbnail>,
    imageLoader: ImageLoader,
    onThumbnailPagerChanged: (Int) -> Unit,
    onSliderValueChange: (Float) -> Unit,
    onSliderValueChangeFinished: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onPlayPauseClick: () -> Unit
) {

    val windowInsets = WindowInsets.systemBars

    val transition = updateTransition(currentlyPlayingMusicTrack.accent, label = "")

    val firstColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = BACKGROUND_ANIMATION_DURATION) },
        label = ""
    ) { accentColor ->
        Color.fromHex(accentColor).copy(alpha = 0.6f)
    }

    val secondColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = BACKGROUND_ANIMATION_DURATION) },
        label = ""
    ) { accentColor ->
        Color.fromHex(accentColor).copy(alpha = 0.2f)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(firstColor, secondColor)
                )
            )
            .padding(paddingValues = windowInsets.asPaddingValues())
    ) {

        Box(
            modifier = Modifier
                .height(SizeMini4)
                .width(SizeHuge48)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
        )

        PlayerThumbnailPager(
            modifier = Modifier
                .fillMaxWidth(),
            items = musicTrackThumbnailList,
            imageLoader = imageLoader,
            currentPlayingMusicTrackIndex = currentlyPlayingMusicTrackIndex,
            onThumbnailPagerChanged = onThumbnailPagerChanged
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currentlyPlayingMusicTrack.name,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = currentlyPlayingMusicTrack.artist,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Normal
                )
            )

            Spacer(modifier = Modifier.height(SizeLarge24))

            TrackSeekBar(
                modifier = Modifier.padding(horizontal = StandardScreenPadding),
                totalDuration = totalDuration,
                value = sliderPosition,
                onValueChange = onSliderValueChange,
                currentPosition = currentPosition,
                onValueChangeFinished = onSliderValueChangeFinished
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            StandardIconButton(
                icon = R.drawable.play_skip_back,
                color = MaterialTheme.colorScheme.onBackground,
                enabled = !isLoading,
                onClick = onPreviousClick
            )

            Spacer(modifier = Modifier.width(SizeStandard16))

            StandardIconButton(
                modifier = Modifier.size(PlayPauseButtonSize),
                onClick = onPlayPauseClick,
                icon = if (isPlaying) {
                    R.drawable.pause
                } else R.drawable.play,
                color = MaterialTheme.colorScheme.background,
                backgroundColor = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.width(SizeStandard16))

            StandardIconButton(
                icon = R.drawable.play_skip_forward,
                color = MaterialTheme.colorScheme.onBackground,
                enabled = !isLoading,
                onClick = onNextClick
            )
        }

        Spacer(modifier = Modifier.width(SizeStandard16))
    }
}