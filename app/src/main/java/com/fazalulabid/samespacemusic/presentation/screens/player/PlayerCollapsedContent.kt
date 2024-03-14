package com.fazalulabid.samespacemusic.presentation.screens.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fazalulabid.samespacemusic.R
import com.fazalulabid.samespacemusic.presentation.components.StandardIconButton
import com.fazalulabid.samespacemusic.presentation.ui.theme.CollapsedPlayerSize

@Composable
fun PlayerCollapsedContent(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .background(Color.Red)
            .fillMaxSize()
            .drawBehind {
                val progress = 0.9f

                drawLine(
                    color = Color.White,
                    start = Offset(x = 0f, y = 1.dp.toPx()),
                    end = Offset(x = size.width * progress, y = 1.dp.toPx()),
                    strokeWidth = 2.dp.toPx()
                )
            }
    ) {
        Spacer(
            modifier = Modifier
                .width(2.dp)
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(CollapsedPlayerSize)
        ) {
            AsyncImage(
                model = "",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .size(48.dp)
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(CollapsedPlayerSize)
                .weight(1f)
        ) {
            BasicText(
                text = "The First Song",
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            BasicText(
                text = "Tylor Swift",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Spacer(
            modifier = Modifier
                .width(2.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(CollapsedPlayerSize)
        ) {
            StandardIconButton(
                icon = if (true) R.drawable.pause else R.drawable.play,
                color = MaterialTheme.colorScheme.onBackground,
                onClick = {
                    if (true) {
                        // Pause Song
                    } else {
                        // Play Song
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 8.dp)
                    .size(20.dp)
            )

            StandardIconButton(
                icon = R.drawable.play_skip_forward,
                color = MaterialTheme.colorScheme.onBackground,
                onClick = {
                    // Force seek to next song
                },
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 8.dp)
                    .size(20.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .width(2.dp)
        )
    }
}