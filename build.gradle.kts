// Top-level build file: plugin versions are declared here and applied in :app
plugins {
    id("com.android.application") version "8.7.3" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    // Since Kotlin 2.0 the Compose compiler is a Kotlin plugin
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
}
