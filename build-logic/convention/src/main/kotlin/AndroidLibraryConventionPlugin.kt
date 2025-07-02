import com.android.build.gradle.LibraryExtension
import com.example.hnote.configureKotlinAndroid
import com.example.hnote.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.android")
            apply(plugin = "nowinandroid.android.lint")

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 36
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                testOptions.animationsDisabled = true
                resourcePrefix =
                    path.split("""\W""".toRegex())
                        .drop(1)
                        .distinct()
                        .joinToString(separator = "_")
                        .lowercase()
                        .plus("_")
            }
            dependencies {
                add("androidTestImplementation", libs.findLibrary("kotlin.test").get())
                add("testImplementation", libs.findLibrary("kotlin.test").get())
                add("implementation", libs.findLibrary("androidx.tracing.ktx").get())
            }
        }
    }
}