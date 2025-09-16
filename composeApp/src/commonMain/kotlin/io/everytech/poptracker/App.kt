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
import io.everytech.poptracker.ui.screens.AddProductScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    PopTrackerTheme {
        var currentScreen by remember { mutableStateOf(Screen.Tracker.route) }
        
        when (currentScreen) {
            Screen.Tracker.route -> {
                TrackerScreen(
                    modifier = Modifier.fillMaxSize(),
                    onNavigateToAddProduct = {
                        currentScreen = Screen.AddProduct.route
                    }
                )
            }
            Screen.AddProduct.route -> {
                AddProductScreen(
                    modifier = Modifier.fillMaxSize(),
                    initialCountryCode = "sg", // Default to Singapore
                    onBackClick = {
                        currentScreen = Screen.Tracker.route
                    },
                    onProductSaved = {
                        currentScreen = Screen.Tracker.route
                    }
                )
            }
        }
    }
}