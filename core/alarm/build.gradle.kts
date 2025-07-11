plugins {
    alias(libs.plugins.hnote.android.library)
    alias(libs.plugins.hnote.hilt)
}

android {
    namespace = "com.example.hnote.core.alarm"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.notification)
}