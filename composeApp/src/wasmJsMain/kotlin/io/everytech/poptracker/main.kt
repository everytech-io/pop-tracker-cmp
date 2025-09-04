package io.everytech.poptracker

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    println("PopTracker web app starting...")
    try {
        ComposeViewport(document.body!!) {
            App()
        }
        println("PopTracker web app initialized successfully")
    } catch (e: Exception) {
        println("Error initializing PopTracker: ${e.message}")
        window.alert("Error loading PopTracker: ${e.message}")
    }
}