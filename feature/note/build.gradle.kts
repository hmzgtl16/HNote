plugins {
    alias(libs.plugins.hnote.android.feature)
    alias(libs.plugins.hnote.android.library.compose)
}

android {
    namespace = "com.example.hnote.feature.note"
}

dependencies {
    implementation(projects.core.data)
}