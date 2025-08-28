#!/bin/sh

# Xcode Cloud script for Kotlin Multiplatform setup
# This script runs after the repository is cloned

set -e

echo "🚀 Starting KMP Xcode Cloud setup..."

# JDK Version
JDK_VERSION="20.0.1"
JDK_URL="https://download.oracle.com/java/20/archive/jdk-${JDK_VERSION}_macos-x64_bin.tar.gz"
JDK_ARM64_URL="https://download.oracle.com/java/20/archive/jdk-${JDK_VERSION}_macos-aarch64_bin.tar.gz"

# Detect architecture
ARCH=$(uname -m)
echo "📱 Detected architecture: $ARCH"

# Set JDK URL based on architecture
if [ "$ARCH" = "arm64" ]; then
    DOWNLOAD_URL=$JDK_ARM64_URL
    echo "🍎 Using ARM64 JDK for Apple Silicon"
else
    DOWNLOAD_URL=$JDK_URL
    echo "💻 Using x64 JDK for Intel"
fi

# Set paths
JDK_DIR="$CI_WORKSPACE/DerivedData/JDK"
JAVA_HOME="$JDK_DIR/Home"
GRADLE_CACHE_DIR="$CI_WORKSPACE/DerivedData/GradleCache"

echo "📁 JDK will be installed at: $JAVA_HOME"

# Create directories
mkdir -p "$JDK_DIR"
mkdir -p "$GRADLE_CACHE_DIR"

# Download and install JDK if not already present
if [ ! -d "$JAVA_HOME" ]; then
    echo "⬇️  Downloading JDK $JDK_VERSION..."
    curl -o "$JDK_DIR/jdk.tar.gz" "$DOWNLOAD_URL"
    
    echo "📦 Extracting JDK..."
    cd "$JDK_DIR"
    tar -xzf jdk.tar.gz
    
    # Find the extracted JDK directory and rename it to "Home"
    JDK_EXTRACTED=$(find . -name "jdk-*" -type d | head -n 1)
    if [ -n "$JDK_EXTRACTED" ]; then
        mv "$JDK_EXTRACTED" Home
        echo "✅ JDK installed successfully"
    else
        echo "❌ Failed to find extracted JDK directory"
        exit 1
    fi
    
    # Clean up
    rm jdk.tar.gz
else
    echo "✅ JDK already installed"
fi

# Set environment variables
export JAVA_HOME="$JAVA_HOME"
export PATH="$JAVA_HOME/bin:$PATH"
export GRADLE_USER_HOME="$GRADLE_CACHE_DIR"

echo "🔧 Environment setup:"
echo "   JAVA_HOME: $JAVA_HOME"
echo "   GRADLE_USER_HOME: $GRADLE_USER_HOME"

# Verify Java installation
java -version
javac -version

echo "🎯 Making gradlew executable..."
chmod +x ../gradlew

echo "🏗️  Pre-building KMP shared framework..."
cd ..
./gradlew :composeApp:embedAndSignAppleFrameworkForXcode

echo "✅ KMP Xcode Cloud setup completed successfully!"