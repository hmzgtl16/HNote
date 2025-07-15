plugins {
    alias(libs.plugins.hnotes.jvm.library)
    alias(libs.plugins.hnotes.hilt)
}

dependencies {
    implementation(libs.org.jetbrains.kotlinx.coroutines.core)
}