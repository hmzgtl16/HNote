plugins {
    alias(libs.plugins.hnote.android.library)
}

android {
    namespace = "com.example.hnote.core.domain"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)

    implementation(libs.javax.inject)
}