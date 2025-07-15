plugins {
    alias(libs.plugins.hnotes.android.library)
    alias(libs.plugins.hnotes.hilt)
}

android {
    namespace = "com.example.hnotes.core.notification"
}

dependencies {
    implementation(projects.core.navigation)
}