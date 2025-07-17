import com.android.build.gradle.LibraryExtension
import com.example.hnotes.configureGradleManagedDevices
import com.example.hnotes.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("hnotes-android-library").get().get().pluginId)
            apply(plugin = libs.findPlugin("hnotes-hilt").get().get().pluginId)
            apply(plugin = libs.findPlugin("hnotes-module-graph").get().get().pluginId)
            apply(plugin = libs.findPlugin("com-android-compose-screenshot").get().get().pluginId)

            extensions.configure<LibraryExtension> {
                testOptions.animationsDisabled = true
                configureGradleManagedDevices(this)
                experimentalProperties["android.experimental.enableScreenshotTest"] = true
            }

            dependencies {
                add(
                    "implementation",
                    project(":core:data")
                )
                add(
                    "implementation",
                    project(":core:ui")
                )
                add(
                    "implementation",
                    project(":core:navigation")
                )
                add(
                    "implementation",
                    libs.findLibrary("androidx-hilt-navigation-compose").get()
                )
                add(
                    "implementation",
                    libs.findLibrary("androidx-lifecycle-runtime-compose").get()
                )
                add(
                    "implementation",
                    libs.findLibrary("androidx-lifecycle-viewmodel-compose").get()
                )
                add(
                    "screenshotTestImplementation",
                    libs.findLibrary("com-android-tools-screenshot-validation-api").get()
                )
                add(
                    "screenshotTestImplementation",
                    libs.findLibrary("androidx-compose-ui-tooling").get()
                )
            }
        }
    }
}