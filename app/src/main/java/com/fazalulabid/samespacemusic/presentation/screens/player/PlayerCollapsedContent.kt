package com.fazalulabid.samespacemusic.presentation.screens.player

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.fazalulabid.samespacemusic.R
import com.fazalulabid.samespacemusic.core.util.Constants
import com.fazalulabid.samespacemusic.domain.model.MusicTrack
import com.fazalulabid.samespacemusic.presentation.components.StandardIconButton
import com.fazalulabid.samespacemusic.presentation.ui.theme.AvatarSize
import com.fazalulabid.samespacemusic.presentation.ui.theme.SizeSmall8
import com.fazalulabid.samespacemusic.presentation.ui.theme.StandardScreenPadding
import com.fazalulabid.samespacemusic.presentation.util.fromHex

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerCollapsedContent(
    modifier: Modifier = Modifier,
    currentMusicTrack: MusicTrack,
    imageLoader: ImageLoader,
    thumbnailSize: Dp = AvatarSize,
    nextAndPreviousColors: Pair<Color?, Color?>,
    thumbnailShape: Shape = MaterialTheme.shapes.large,
    isPlaying: Boolean,
    onSwipeToLeft: () -> Unit,
    onSwipeToRight: () -> Unit,
    onClick: () -> Unit,
    onActionClick: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onSwipeToRight()
                    false
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    onSwipeToLeft()
                    false
                }

                else -> {
                    false
                }
            }
        },
        positionalThreshold = { it }
    )
    val transition = updateTransition(currentMusicTrack.accent, label = "")
    val firstColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = Constants.BACKGROUND_ANIMATION_DURATION) },
        label = ""
    ) { accentColor ->
        Color.fromHex(accentColor).copy(alpha = 0.7f)
    }

    val secondColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = Constants.BACKGROUND_ANIMATION_DURATION) },
        label = ""
    ) { accentColor ->
        Color.fromHex(accentColor).copy(alpha = 0.8f)
    }

    val thirdColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = Constants.BACKGROUND_ANIMATION_DURATION) },
        label = ""
    ) { accentColor ->
        Color.fromHex(accentColor).copy(alpha = 0.9f)
    }

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            CollapsedPlayerDismissBackground(
                dismissState = dismissState,
                nextAndPreviousColors = nextAndPreviousColors
            )
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row(
                modifier = Modifier
                    .clickable { onClick() }
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(firstColor, secondColor, thirdColor)
                        )
                    )
                    .padding(horizontal = StandardScreenPadding, vertical = SizeSmall8),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = currentMusicTrack.getCoverImageUrl(),
                            imageLoader = imageLoader
                        ),
                        contentDescription = stringResource(
                            R.string.cover_image,
                            currentMusicTrack.name
                        ),
                        modifier = Modifier
                            .size(thumbnailSize)
                            .clip(thumbnailShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(StandardScreenPadding))
                    Text(
                        text = currentMusicTrack.name,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                StandardIconButton(
                    onClick = onActionClick,
                    icon = if (isPlaying) {
                        R.drawable.pause
                    } else R.drawable.play,
                    color = MaterialTheme.colorScheme.background,
                    backgroundColor = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsedPlayerDismissBackground(
    modifier: Modifier = Modifier,
    dismissState: SwipeToDismissBoxState,
    nextAndPreviousColors: Pair<Color?, Color?>
) {
    val color = if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) {
        nextAndPreviousColors.second
    } else nextAndPreviousColors.first

    val text = if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) {
        if (nextAndPreviousColors.second != null) "Previous Track" else "Nothing there"
    } else {
        if (nextAndPreviousColors.first != null) "Next Track" else "Nothing there"
    }

    val alignment = if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) {
        Alignment.CenterStart
    } else Alignment.CenterEnd

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color ?: MaterialTheme.colorScheme.background)
            .padding(horizontal = StandardScreenPadding)
    ) {
        Text(
            modifier = Modifier.align(alignment),
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}