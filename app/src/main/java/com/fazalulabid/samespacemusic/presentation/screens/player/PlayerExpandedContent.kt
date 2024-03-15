package com.fazalulabid.samespacemusic.presentation.screens.player

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fazalulabid.samespacemusic.R
import com.fazalulabid.samespacemusic.domain.model.MusicTrack
import com.fazalulabid.samespacemusic.presentation.components.SeekBar
import com.fazalulabid.samespacemusic.presentation.components.StandardIconButton
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
    currentlyPlayingMusicTrack: MusicTrack
) {
    val windowInsets = WindowInsets.systemBars

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        Color.fromHex(currentlyPlayingMusicTrack.accent).copy(alpha = 0.5f),
                        MaterialTheme.colorScheme.background
                    )
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
                .fillMaxWidth()
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

            SeekBar(
                modifier = Modifier.padding(horizontal = StandardScreenPadding),
                onValueChange = {}
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
                onClick = {

                }
            )

            Spacer(modifier = Modifier.width(SizeStandard16))

            StandardIconButton(
                modifier = Modifier.size(PlayPauseButtonSize),
                onClick = {

                },
                icon = if (true) {
                    R.drawable.pause
                } else R.drawable.play,
                color = MaterialTheme.colorScheme.background,
                backgroundColor = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.width(SizeStandard16))

            StandardIconButton(
                icon = R.drawable.play_skip_forward,
                color = MaterialTheme.colorScheme.onBackground,
                onClick = {
                    // Force go to next song
                }
            )
        }

        Spacer(modifier = Modifier.width(SizeStandard16))
    }
}