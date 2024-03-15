package com.fazalulabid.samespacemusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import coil.ImageLoader
import com.fazalulabid.samespacemusic.presentation.screens.home.HomeScreen
import com.fazalulabid.samespacemusic.presentation.ui.theme.SamespaceMusicTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SamespaceMusicTheme {
                val density = LocalDensity.current
                val windowsInsets = WindowInsets.systemBars
                val bottomDp = with(density) { windowsInsets.getBottom(density).toDp() }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        paddingValues = innerPadding,
                        imageLoader = imageLoader
                    )
                }
            }
        }
    }
}