plugins {
    alias(libs.plugins.hnotes.android.library)
    alias(libs.plugins.hnotes.hilt)
}

android {
    namespace = "com.example.hnotes.core.alarm"
}

dependencies {
    api(projects.core.model)
    api(projects.core.notification)
}