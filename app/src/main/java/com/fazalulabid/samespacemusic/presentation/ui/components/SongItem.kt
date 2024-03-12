package com.fazalulabid.samespacemusic.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import coil.compose.rememberAsyncImagePainter
import com.fazalulabid.samespacemusic.R
import com.fazalulabid.samespacemusic.domain.model.Song
import com.fazalulabid.samespacemusic.presentation.ui.theme.AvatarSize
import com.fazalulabid.samespacemusic.presentation.ui.theme.SpaceExtraExtraSmall
import com.fazalulabid.samespacemusic.presentation.ui.theme.SpaceMedium
import com.fazalulabid.samespacemusic.presentation.ui.theme.SpaceSmall

@Composable
fun SongItem(
    song: Song,
    thumbnailSize: Dp = AvatarSize,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = SpaceMedium, vertical = SpaceSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                song.getCoverImageUrl()
            ), contentDescription = stringResource(R.string.cover_image, song.name),
            modifier = Modifier
                .size(thumbnailSize)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(SpaceMedium))
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = song.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(SpaceExtraExtraSmall))
            Text(
                text = song.artist,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}