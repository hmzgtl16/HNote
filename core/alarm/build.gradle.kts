plugins {
    alias(libs.plugins.hnotes.android.library)
    alias(libs.plugins.hnotes.hilt)
    alias(libs.plugins.hnotes.module.graph)
}

android {
    namespace = "com.example.hnotes.core.alarm"
}

dependencies {
    api(projects.core.model)
    implementation(projects.core.notification)
}