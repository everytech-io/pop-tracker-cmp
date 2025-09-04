package io.everytech.poptracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import io.everytech.poptracker.ui.theme.PopTrackerTheme
import io.everytech.poptracker.navigation.Screen
import io.everytech.poptracker.navigation.bottomNavItems
import io.everytech.poptracker.ui.screens.TrackerScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
        PopTrackerTheme {
            TrackerScreen(modifier = Modifier)
        }
}