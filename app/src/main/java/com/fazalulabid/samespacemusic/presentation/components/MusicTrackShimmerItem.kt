package com.fazalulabid.samespacemusic.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.fazalulabid.samespacemusic.presentation.ui.theme.AvatarSize
import com.fazalulabid.samespacemusic.presentation.ui.theme.SizeStandard16
import com.fazalulabid.samespacemusic.presentation.util.shimmerEffect

@Composable
fun MusicTrackShimmerItem(
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(SizeStandard16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(AvatarSize)
                .clip(CircleShape)
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.width(SizeStandard16))
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                Modifier
                    .height(MaterialTheme.typography.bodySmall.fontSize.value.dp)
                    .fillMaxWidth(0.4f)
                    .clip(MaterialTheme.shapes.large)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(SizeStandard16))
            Box(
                Modifier
                    .height(MaterialTheme.typography.bodySmall.fontSize.value.dp)
                    .fillMaxWidth(0.3f)
                    .clip(MaterialTheme.shapes.large)
                    .shimmerEffect()
            )
        }
    }
}