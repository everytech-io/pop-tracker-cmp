package io.everytech.poptracker

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.analytics
import dev.gitlive.firebase.analytics.logEvent

fun testFirebaseAnalytics() {
    try {
        // Log a test event
        Firebase.analytics.logEvent("app_test_event") {
            param("test_source", "kmp_verification")
            param("platform", "android")
            param("timestamp", System.currentTimeMillis())
        }
        
        // Log screen view
        Firebase.analytics.logEvent("screen_view") {
            param("screen_name", "test_screen")
            param("screen_class", "FirebaseAnalyticsTest")
        }
        
        println("Firebase Analytics: Test events logged successfully")
    } catch (e: Exception) {
        println("Firebase Analytics Error: ${e.message}")
        e.printStackTrace()
    }
}