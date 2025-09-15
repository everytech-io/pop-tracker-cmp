import SwiftUI
import Firebase

@main
struct iOSApp: App {
    init() {
        // Initialize GitLive Firebase SDK for iOS
        FirebaseApp.configure()
//        FirebaseAnalytics
//        // Log test event for verification
//        Firebase_analyticsKt.logEvent(Firebase_analyticsKt.analytics, "app_test_event") { builder in
//            _ = builder.param(key: "test_source", value: "ios_verification")
//            _ = builder.param(key: "platform", value: "ios")
//        }
        
        print("Firebase Analytics: iOS initialized with GitLive SDK")
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
