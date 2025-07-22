package je.ramos.poptracker.navigation

import org.jetbrains.compose.resources.DrawableResource
import poptracker.composeapp.generated.resources.Res
import poptracker.composeapp.generated.resources.compose_multiplatform
import poptracker.composeapp.generated.resources.icon_collection
import poptracker.composeapp.generated.resources.icon_explore
import poptracker.composeapp.generated.resources.icon_profile

sealed class Screen(
    val route: String,
    val icon: DrawableResource,
    val title: String
) {
    object Explore : Screen(route = "explore", icon = Res.drawable.icon_explore, title = "Explore")
    object Collection : Screen("collection", Res.drawable.icon_collection, "Collection")
    object Profile : Screen("profile", Res.drawable.icon_profile, "Profile")
}

val bottomNavItems = listOf(
    Screen.Explore,
    Screen.Collection,
    Screen.Profile
)