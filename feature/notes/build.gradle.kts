plugins {
    alias(libs.plugins.hnote.android.feature)
    alias(libs.plugins.hnote.android.library.compose)
}

android {
    namespace = "com.example.notes.feature.notes"
}

dependencies {
    implementation(projects.core.domain)
}
