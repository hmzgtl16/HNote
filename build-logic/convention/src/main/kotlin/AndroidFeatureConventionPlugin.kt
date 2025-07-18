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

            extensions.configure<LibraryExtension> {
                testOptions.animationsDisabled = true
                configureGradleManagedDevices(this)
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
                    "testImplementation",
                    libs.findLibrary("androidx-navigation-testing").get()
                )
                add(
                    "androidTestImplementation",
                    libs.findLibrary("androidx-compose-ui-test").get()
                )
                add(
                    "androidTestImplementation",
                    libs.findLibrary("androidx-compose-ui-test-manifest").get()
                )
                add(
                    "androidTestImplementation",
                    libs.findLibrary("androidx-lifecycle-runtime-testing").get()
                )
            }
        }
    }
}