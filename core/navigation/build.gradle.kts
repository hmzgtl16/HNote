plugins {
    alias(libs.plugins.hnotes.android.library)
    alias(libs.plugins.hnotes.android.library.compose)
    alias(libs.plugins.hnotes.hilt)
    alias(libs.plugins.org.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.example.hnotes.core.navigation"
}

dependencies {
    api(libs.androidx.navigation.compose)

    implementation(libs.org.jetbrains.kotlinx.coroutines.core)
}