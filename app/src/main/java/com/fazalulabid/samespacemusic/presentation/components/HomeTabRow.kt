package com.fazalulabid.samespacemusic.presentation.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fazalulabid.samespacemusic.R
import com.fazalulabid.samespacemusic.presentation.screens.home.TabItem
import com.fazalulabid.samespacemusic.presentation.ui.theme.SpaceExtraSmall
import com.fazalulabid.samespacemusic.presentation.ui.theme.SpaceLarge
import com.fazalulabid.samespacemusic.presentation.util.NoRippleTheme

@Composable
fun HomeTabRow(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    tabItems: List<TabItem>,
    onTabClick: (Int) -> Unit
) {
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = modifier
                .padding(horizontal = SpaceLarge),
            indicator = {},
            divider = {},
            containerColor = Color.Transparent
        ) {
            tabItems.forEachIndexed { index, tabItem ->
                val dothAlpha by animateFloatAsState(
                    targetValue = if (index == selectedTabIndex) 1f else 0f, label = "",
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { onTabClick(index) },
                ) {
                    Text(
                        text = tabItem.title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = if (index == selectedTabIndex) {
                                MaterialTheme.colorScheme.onBackground
                            } else MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Spacer(modifier = Modifier.height(SpaceExtraSmall))
                    Icon(
                        modifier = Modifier
                            .size(SpaceExtraSmall)
                            .graphicsLayer {
                                alpha = dothAlpha
                                translationY = (1f - dothAlpha) * 48.dp.toPx()
                                rotationZ = 0f
                            },
                        painter = painterResource(id = R.drawable.ic_circle),
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.height(SpaceLarge))
                }
            }
        }
    }
}