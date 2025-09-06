# Preserve annotations
-keepattributes *Annotation*

# Keep generic type information for Kotlin
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# Kotlin specific
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

# Compose specific
-keep class androidx.compose.** { *; }
-keep class androidx.navigation.** { *; }
-keep class androidx.lifecycle.** { *; }
-dontwarn androidx.compose.**

# Kotlin Multiplatform
-keep class io.everytech.poptracker.** { *; }
-keepclassmembers class io.everytech.poptracker.** { *; }

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# Keep data classes
-keepclassmembers class * {
    public ** component1();
    public ** component2();
    public ** component3();
    public ** component4();
    public ** component5();
}

# Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep @Composable functions
-keep @androidx.compose.runtime.Composable class * { *; }
-keep @androidx.compose.ui.tooling.preview.Preview class * { *; }

# Keep ViewModels
-keep class * extends androidx.lifecycle.ViewModel {
    <init>();
}

# For debugging (remove in production)
-keepattributes SourceFile,LineNumberTable