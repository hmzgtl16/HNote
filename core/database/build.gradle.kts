plugins {
    alias(libs.plugins.hnote.android.library)
    alias(libs.plugins.hnote.android.room)
    alias(libs.plugins.hnote.hilt)
}

android {
    namespace = "com.example.hnote.core.database"
}

dependencies {
    api(projects.core.model)

    implementation(libs.org.jetbrains.kotlinx.datetime)

    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
}