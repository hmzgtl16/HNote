plugins {
    alias(libs.plugins.hnotes.android.feature)
    alias(libs.plugins.hnotes.android.library.compose)
    alias(libs.plugins.hnotes.screenshot.test)
}

android {
    namespace = "com.example.hnotes.feature.note"
}