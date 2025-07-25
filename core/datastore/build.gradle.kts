plugins {
    alias(libs.plugins.hnotes.android.library)
    alias(libs.plugins.hnotes.hilt)
    alias(libs.plugins.hnotes.module.graph)
    alias(libs.plugins.com.google.protobuf)
}

android {
    namespace = "com.example.hnotes.core.datastore"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

protobuf {

    protoc {
        artifact = libs.com.google.protobuf.protoc.get().toString()
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)

    api(libs.androidx.dataStore)

    implementation(libs.com.google.protobuf.kotlin.lite)

    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
}