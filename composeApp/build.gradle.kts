import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    id("com.google.gms.google-services")
    kotlin("plugin.serialization") version "1.9.0" // This lines

}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            binaryOption("bundleId", "io.everytech.poptracker")
        }
    }
    
    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName.set("composeApp")
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

//            implementation(project.dependencies.platform("com.google.firebase:firebase-bom:34.2.0"))
//
//
//            // TODO: Add the dependencies for Firebase products you want to use
//            // When using the BoM, don't specify versions in Firebase dependencies
//            implementation("com.google.firebase:firebase-analytics")
        }
        iosMain.dependencies {
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation("org.jetbrains.compose.material3:material3:1.9.0-alpha04")
            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.materialIconsExtended)
            implementation("dev.gitlive:firebase-common:2.1.0")
            implementation("dev.gitlive:firebase-analytics:2.1.0")
            implementation("dev.gitlive:firebase-firestore:2.1.0")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1") // This line
//            implementation(libs.androidx.lifecycle.runtime.compose)
//            implementation(libs.androidx.lifecycle.viewmodel.compose)
//            implementation(libs.androidx.navigation.compose)
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-runtime-compose:2.9.2")
// ViewModel utilities for Compose
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.9.2")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

android {
    namespace = "io.everytech.poptracker"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "io.everytech.poptracker"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    signingConfigs {
        create("release") {
            storeFile = file(System.getProperty("android.injected.signing.store.file", "keystore.jks"))
            storePassword = System.getProperty("android.injected.signing.store.password", "")
            keyAlias = System.getProperty("android.injected.signing.key.alias", "")
            keyPassword = System.getProperty("android.injected.signing.key.password", "")
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
             signingConfig = signingConfigs.getByName("release") // Commented out for unsigned build
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "io.everytech.poptracker.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "io.everytech.poptracker"
            packageVersion = "1.0.0"
        }
    }
}
