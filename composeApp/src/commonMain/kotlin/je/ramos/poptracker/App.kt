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
import je.ramos.poptracker.ui.theme.PopTrackerTheme
import je.ramos.poptracker.navigation.Screen
import je.ramos.poptracker.navigation.bottomNavItems
import je.ramos.poptracker.ui.screens.TrackerScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
        PopTrackerTheme {
            TrackerScreen(modifier = Modifier)
        }
}