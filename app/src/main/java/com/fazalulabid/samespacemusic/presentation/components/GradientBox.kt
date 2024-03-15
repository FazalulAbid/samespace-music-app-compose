package com.fazalulabid.samespacemusic.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GradientBox(
    modifier: Modifier = Modifier,
    gradientColor: Color = MaterialTheme.colorScheme.background,
    bottomGradientHeight: Dp = 100.dp,
    isCurrentlyPlaying: Boolean = false,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        if (!isCurrentlyPlaying) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(bottomGradientHeight)
                    .background(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                0F to Color.Transparent,
                                0.4F to gradientColor.copy(alpha = 0.9F),
                                0.6F to gradientColor.copy(alpha = 1F),
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY,
                            tileMode = TileMode.Clamp
                        )
                    )
            )
        }
        content()
    }
}