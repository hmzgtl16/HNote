import com.android.build.gradle.LibraryExtension
import com.example.hnotes.libs
import io.github.takahirom.roborazzi.RoborazziExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class ScreenshotTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("hnotes-android-library").get().get().pluginId)
            apply(plugin = libs.findPlugin("io-github-takahirom-roborazzi").get().get().pluginId)

            extensions.configure<LibraryExtension> {
                testOptions {
                    unitTests {
                        isIncludeAndroidResources = true
                        isReturnDefaultValues = true
                        all {
                            it.systemProperties["robolectric.pixelCopyRenderMode"] = "hardware"
                        }
                    }
                }
            }

            extensions.configure<RoborazziExtension> {
                outputDir.set(file("src/screenshots"))
                compare {
                    outputDir.set(file("src/screenshots"))
                }
                generateComposePreviewRobolectricTests {
                    enable.set(true)
                    packages.add("com.example.hnotes")
                }
            }

            dependencies {
                add(
                    "testImplementation",
                    libs.findLibrary("junit").get()
                )
                add(
                    "testImplementation",
                    libs.findLibrary("androidx-compose-ui-test").get()
                )
                add(
                    "testImplementation",
                    libs.findLibrary("androidx-compose-ui-test-manifest").get()
                )
                add(
                    "testImplementation",
                    libs.findLibrary("org-robolectric").get()
                )
                add(
                    "testImplementation",
                    libs.findLibrary("io-github-takahirom-roborazzi-compose").get()
                )
                add(
                    "testImplementation",
                    libs.findLibrary("io-github-takahirom-roborazzi-compose-preview-scanner-support")
                        .get()
                )
                add(
                    "testImplementation",
                    libs.findLibrary("io-github-sergio-sastre-composable-preview-scanner-android")
                        .get()
                )
            }
        }
    }
}