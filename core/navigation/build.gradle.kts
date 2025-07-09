plugins {
    alias(libs.plugins.hnote.android.library)
    alias(libs.plugins.hnote.android.library.compose)
    alias(libs.plugins.hnote.hilt)
}

android {
    namespace = "com.example.hnote.core.navigation"
}

dependencies {
    api(libs.androidx.navigation.compose)

    implementation(libs.org.jetbrains.kotlinx.coroutines.core)
}