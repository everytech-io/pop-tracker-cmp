# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Kotlin Multiplatform (KMP) Pop Tracker project using Compose Multiplatform for UI. The project targets Android, iOS, and Desktop (JVM) platforms.

## Essential Development Commands

### Running the Application

```bash
# Desktop (with hot reload)
./gradlew desktopRunDev

# Android (requires connected device/emulator)
./gradlew installDebug

# iOS (requires macOS and Xcode)
# Open iosApp/iosApp.xcodeproj in Xcode and run
```

### Building

```bash
# Build all platforms
./gradlew build

# Platform-specific builds
./gradlew assembleDebug              # Android APK
./gradlew packageDistributionForCurrentOS  # Desktop package
./gradlew linkDebugFrameworkIosArm64      # iOS framework
```

### Testing and Quality

```bash
# Run all tests
./gradlew check

# Fix lint issues
./gradlew lintFix

# Clean build
./gradlew clean build
```

## Architecture

The project follows a single-module KMP structure:

```
composeApp/
├── commonMain/     # Shared code for all platforms
├── androidMain/    # Android-specific implementations
├── desktopMain/    # Desktop (JVM) implementations
└── iosMain/        # iOS-specific implementations
```

Key architectural patterns:
- **Expect/Actual**: Platform-specific implementations use the expect/actual pattern (see `Platform` interface)
- **Compose Multiplatform**: Shared UI components across all platforms
- **Material3**: Design system for consistent UI

## Platform Configuration

- **Android**: Min SDK 24, Target/Compile SDK 35
- **iOS**: Supports x64 (Intel), arm64 (devices), and simulatorArm64 (Apple Silicon)
- **Desktop**: JVM-based, packages to MSI (Windows), DMG (macOS), DEB (Linux)

## Dependencies

Key versions (defined in `gradle/libs.versions.toml`):
- Kotlin: 2.2.0
- Compose Multiplatform: 1.8.2
- Coroutines: 1.10.2

## Development Guidelines

1. **Platform-specific code**: Use expect/actual pattern in appropriate source sets
2. **UI Components**: Place in `commonMain` for maximum code sharing
3. **Resources**: Use Compose Multiplatform's resource system for images/strings
4. **Testing**: Write tests in appropriate source sets (commonTest, androidTest, etc.)

## Project Entry Points

- **Android**: `AndroidManifest.xml` → `MainActivity`
- **Desktop**: `Main.kt` → `main()` function
- **iOS**: `iosApp.swift` → `ContentView`

## EveryUI Design System

### EveryImage Component

Atomic image component with opinionated aspect ratios and semantic tokens:

**Key Features:**
- **Semantic Aspect Ratios**: `AspectRatioToken` with meaningful names (Square, Landscape, Portrait, Photo, PhotoPortrait, Ultrawide, Golden)
- **Config Pattern**: `EveryImageConfig` for all customization options
- **Loading States**: Built-in loading indicators and error handling
- **Token-based**: Uses `ShapeToken` and `PaddingToken` for consistency
- **Dual Source Support**: `ImageSource.Drawable` for resources, `ImageSource.Url` for remote images

**Usage:**
```kotlin
EveryImage(
    source = ImageSource.Drawable(painterResource(Res.drawable.product)),
    config = EveryImageConfig(
        aspectRatio = AspectRatioToken.Photo,  // 4:3 - Perfect for products
        shape = ShapeToken.Large,
        padding = PaddingToken.Medium
    )
)
```

**Available Aspect Ratios:**
- Square (1:1) - Profile pictures, icons
- Landscape (16:9) - Video/cinema format
- Portrait (9:16) - Phone screen format
- Photo (4:3) - Traditional photo format
- PhotoPortrait (3:4) - Portrait photo format
- Ultrawide (21:9) - Ultrawide display format
- Golden (1.618:1) - Golden ratio for artistic layouts