package io.everytech.poptracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.FiberNew
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarExitDirection.Companion.Bottom
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.everytech.poptracker.ui.components.product.Product
import io.everytech.poptracker.ui.components.product.ProductCard
import io.everytech.poptracker.ui.components.product.ProductCardConfig
import io.everytech.poptracker.ui.theme.PopTrackerTheme
import io.everytech.poptracker.ui.viewmodels.TrackerViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackerScreen(
    modifier: Modifier = Modifier,
    trackerViewModel: TrackerViewModel = viewModel {
        TrackerViewModel()
    }
) {

    val products = trackerViewModel.products.value
    TrackerScreenContent(modifier = modifier, products = products)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun TrackerScreenContent(
    modifier: Modifier = Modifier,
    products: List<Product>,
) {

    val topAppBarState: TopAppBarState = rememberTopAppBarState()
    val scrollBehavior: TopAppBarScrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)

    // Derive border opacity from scroll state
    val borderOpacity by remember {
        derivedStateOf {
            val maxOffset = -200f // Adjust this value to control how quickly the border appears
            val currentOffset = topAppBarState.heightOffset
            ((-currentOffset / maxOffset).coerceIn(0f, 1f) * 0.12f)
        }
    }

    val exitAlwaysScrollBehavior =
        FloatingToolbarDefaults.exitAlwaysScrollBehavior(exitDirection = Bottom)
    var expanded by rememberSaveable { mutableStateOf(true) }
    val vibrantColors = FloatingToolbarDefaults.vibrantFloatingToolbarColors()
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .nestedScroll(exitAlwaysScrollBehavior),
        topBar = {
            Box {
                LargeTopAppBar(
                    title = {
                        Text(
                            text = "PopTrackr",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        scrolledContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    actions = {
                        IconButton(onClick = { /* Navigate to user profile */ }) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "User Profile"
                            )
                        }
                    }
                )

                // Subtle bottom border that fades in proportionally when scrolled
                if (borderOpacity > 0f) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .height(Dp.Hairline)
                            .background(MaterialTheme.colorScheme.outline.copy(alpha = borderOpacity))
                    )
                }
            }
        },
        // When setting this to `FabPosition.Start` remember to set a
        // `floatingActionButtonPosition = FloatingToolbarHorizontalFabPosition.Start` at the
        // HorizontalFloatingToolbar as well.
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {

            HorizontalFloatingToolbar(
                expanded = expanded,
                floatingActionButton = {
                    // Match the FAB to the vibrantColors. See also StandardFloatingActionButton.
                    FloatingToolbarDefaults.VibrantFloatingActionButton(
                        onClick = { /* doSomething() */ }
                    ) {
                        Icon(Icons.Filled.Search, "Localized description")
                    }
                },
                colors = vibrantColors,
                content = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Outlined.Home, contentDescription = "Localized description")
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Outlined.Schedule, contentDescription = "Localized description")
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Outlined.Bookmarks, contentDescription = "Localized description")
                    }
                },
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 8.dp).background(MaterialTheme.colorScheme.primaryContainer),
            contentPadding = paddingValues
        ) {
            items(items = products) {
                ProductCard(
                    product = it,
                    config = ProductCardConfig().copy(cardCornerRadius = 0.dp)
                )
            }
        }
    }

}

@Preview
@Composable
fun TrackerScreenContentPreview() {
    PopTrackerTheme {

        val sampleProducts = TrackerViewModel().products.value
        TrackerScreenContent(
            modifier = Modifier,
            products = sampleProducts
        )
    }
}