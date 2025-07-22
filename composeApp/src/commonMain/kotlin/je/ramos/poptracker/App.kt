package je.ramos.poptracker

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
import je.ramos.poptracker.everyui.theme.ExtendedTheme
import je.ramos.poptracker.navigation.Screen
import je.ramos.poptracker.navigation.bottomNavItems
import je.ramos.poptracker.ui.screens.CollectionScreen
import je.ramos.poptracker.ui.screens.ExploreScreen
import je.ramos.poptracker.ui.screens.ProfileScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        ExtendedTheme {
            var selectedScreen by remember { mutableStateOf(Screen.Explore.route) }
            
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    NavigationBar {
                        bottomNavItems.forEach { screen ->
                            NavigationBarItem(
                                icon = { Icon(painter = painterResource(screen.icon), contentDescription = screen.title) },
                                label = { Text(screen.title) },
                                selected = selectedScreen == screen.route,
                                onClick = { selectedScreen = screen.route }
                            )
                        }
                    }
                }
            ) { paddingValues ->
                when (selectedScreen) {
                    Screen.Explore.route -> ExploreScreen()
                    Screen.Collection.route -> CollectionScreen()
                    Screen.Profile.route -> ProfileScreen()
                }
            }
        }
    }
}