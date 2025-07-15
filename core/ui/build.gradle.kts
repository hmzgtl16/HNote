plugins {
    alias(libs.plugins.hnotes.android.library)
    alias(libs.plugins.hnotes.android.library.compose)
}

android {
    namespace = "com.example.hnotes.core.ui"
}

dependencies {
    api(projects.core.design)
    api(projects.core.model)
}