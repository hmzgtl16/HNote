plugins {
    alias(libs.plugins.hnotes.android.library)
    alias(libs.plugins.hnotes.android.room)
    alias(libs.plugins.hnotes.hilt)
    alias(libs.plugins.hnotes.module.graph)
}

android {
    namespace = "com.example.hnotes.core.database"
}

dependencies {
    api(projects.core.model)

    implementation(libs.org.jetbrains.kotlinx.datetime)

    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
}