package com.fazalulabid.samespacemusic.presentation.screens.player

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.fazalulabid.samespacemusic.presentation.ui.theme.SizeStandard16
import com.fazalulabid.samespacemusic.presentation.ui.theme.SizeTiny2
import com.fazalulabid.samespacemusic.presentation.ui.theme.StandardScreenPadding
import com.fazalulabid.samespacemusic.presentation.util.fromHex

@Composable
fun PlayerCollapsedContent(
    modifier: Modifier = Modifier,
    currentMusicTrack: MusicTrack,
    imageLoader: ImageLoader,
    thumbnailSize: Dp = AvatarSize,
    thumbnailShape: Shape = MaterialTheme.shapes.large,
    onClick: () -> Unit,
    onActionClick: () -> Unit
) {
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
                onClick = {
                    onActionClick()
                },
                icon = if (true) {
                    R.drawable.pause
                } else R.drawable.play,
                color = MaterialTheme.colorScheme.background,
                backgroundColor = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}