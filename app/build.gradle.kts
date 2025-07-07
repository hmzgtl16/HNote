import com.example.hnote.BuildType

plugins {
    alias(libs.plugins.hnote.android.application)
    alias(libs.plugins.hnote.android.application.compose)
    alias(libs.plugins.hnote.android.application.flavors)
    alias(libs.plugins.hnote.hilt)
    alias(libs.plugins.org.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.example.hnote"

    defaultConfig {
        applicationId = "com.example.hnote"
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = BuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = true
            applicationIdSuffix = BuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.named("debug").get()
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(projects.feature.notes)
    implementation(projects.feature.note)
    implementation(projects.feature.search)
    implementation(projects.feature.settings)

    implementation(projects.core.data)
    implementation(projects.core.design)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.org.jetbrains.kotlinx.serialization.json)
}