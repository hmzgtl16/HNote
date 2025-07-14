import com.android.build.gradle.LibraryExtension
import com.example.hnote.configureGradleManagedDevices
import com.example.hnote.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("hnote.android.library").get().get().pluginId)
            apply(plugin = libs.findPlugin("hnote.hilt").get().get().pluginId)
            apply(
                plugin = libs.findPlugin("org.jetbrains.kotlin.serialization").get().get().pluginId
            )

            extensions.configure<LibraryExtension> {
                testOptions.animationsDisabled = true
                configureGradleManagedDevices(this)
            }

            dependencies {
                add("implementation", project(":core:ui"))
                add("implementation", project(":core:design"))
                add("implementation", project(":core:navigation"))

                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtime.compose").get())
                add(
                    "implementation",
                    libs.findLibrary("androidx.lifecycle.viewmodel.compose").get()
                )
                add("implementation", libs.findLibrary("androidx.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.tracing.ktx").get())
                add(
                    "implementation",
                    libs.findLibrary("org.jetbrains.kotlinx.serialization.json").get()
                )
            }
        }
    }
}