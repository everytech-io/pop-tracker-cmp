package io.everytech.poptracker.navigation

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
    object Tracker : Screen(route = "tracker", icon = Res.drawable.compose_multiplatform, title = "Link Tracker")
    object AddProduct : Screen(route = "add_product", icon = Res.drawable.icon_collection, title = "Add Product")
}

val bottomNavItems = listOf(
    Screen.Tracker
)