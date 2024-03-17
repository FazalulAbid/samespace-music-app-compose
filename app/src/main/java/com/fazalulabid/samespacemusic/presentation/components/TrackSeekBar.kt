package com.fazalulabid.samespacemusic.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import com.fazalulabid.samespacemusic.core.util.convertToMinutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackSeekBar(
    modifier: Modifier = Modifier,
    value: Float,
    sliderEndShape: Shape = MaterialTheme.shapes.large,
    totalDuration: Long,
    currentPosition: Long,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = modifier) {
        Slider(
            modifier = Modifier
                .fillMaxWidth()
                .clip(sliderEndShape),
            value = value,
            colors = SliderDefaults.colors(
                activeTrackColor = MaterialTheme.colorScheme.onBackground,
                inactiveTickColor = MaterialTheme.colorScheme.onSurface
            ),
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = interactionSource,
                    thumbSize = DpSize.Zero
                )
            },
            onValueChange = onValueChange,
            onValueChangeFinished = onValueChangeFinished,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = currentPosition.convertToMinutes(),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Normal
                )
            )
            val remainTime = totalDuration - currentPosition
            Text(
                text = if (remainTime >= 0) remainTime.convertToMinutes() else "",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}