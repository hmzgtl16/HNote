plugins {
    alias(libs.plugins.hnote.jvm.library)
    alias(libs.plugins.hnote.hilt)
}

dependencies {
    implementation(libs.org.jetbrains.kotlinx.coroutines.core)
}