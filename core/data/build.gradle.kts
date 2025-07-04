plugins {
    alias(libs.plugins.hnote.android.library)
    alias(libs.plugins.hnote.hilt)
    alias(libs.plugins.org.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.example.hnote.core.data"

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    api(projects.core.common)
    api(projects.core.database)
    api(projects.core.datastore)

    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
}