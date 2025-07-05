plugins {
    alias(libs.plugins.hnote.android.library)
    alias(libs.plugins.hnote.android.library.compose)
}

android {
    namespace = "com.example.hnote.core.ui"
}

dependencies {
    api(projects.core.design)
    api(projects.core.model)
}