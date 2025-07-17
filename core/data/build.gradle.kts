plugins {
    alias(libs.plugins.hnotes.android.library)
    alias(libs.plugins.hnotes.hilt)
    alias(libs.plugins.hnotes.module.graph)
    alias(libs.plugins.org.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.example.hnotes.core.data"

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    api(projects.core.database)
    api(projects.core.datastore)
    api(projects.core.alarm)

    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
}
