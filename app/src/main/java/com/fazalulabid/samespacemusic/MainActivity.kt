package com.fazalulabid.samespacemusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fazalulabid.samespacemusic.domain.model.Song
import com.fazalulabid.samespacemusic.presentation.ui.components.SongItem
import com.fazalulabid.samespacemusic.presentation.ui.screens.home.HomeScreen
import com.fazalulabid.samespacemusic.presentation.ui.theme.SamespaceMusicTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SamespaceMusicTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(contentPadding = innerPadding)
                }
            }
        }
    }
}