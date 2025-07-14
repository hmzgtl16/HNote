import com.example.hnote.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("com.google.devtools.ksp").get().get().pluginId)

            dependencies {
                add("ksp", libs.findLibrary("com.google.dagger.hilt.compiler").get())
            }

            pluginManager.withPlugin(
                libs.findPlugin("org.jetbrains.kotlin.jvm").get().get().pluginId
            ) {
                dependencies {
                    add("implementation", libs.findLibrary("com.google.dagger.hilt.core").get())
                }
            }

            pluginManager.withPlugin(libs.findPlugin("com.android.base").get().get().pluginId) {
                apply(plugin = libs.findPlugin("com.google.dagger.hilt").get().get().pluginId)
                dependencies {
                    add("implementation", libs.findLibrary("com.google.dagger.hilt.android").get())
                }
            }
        }
    }
}