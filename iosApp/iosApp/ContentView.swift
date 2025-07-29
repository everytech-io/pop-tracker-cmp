import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
                .background(Color(red: 1.0, green: 0.984, blue: 0.968)) // #FFFBF7 background
                .ignoresSafeArea(.container, edges: .bottom) // Extend to bottom safe area
    }
}



