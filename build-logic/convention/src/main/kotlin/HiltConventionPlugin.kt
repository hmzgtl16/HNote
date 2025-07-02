import com.example.hnote.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.google.devtools.ksp")

            dependencies {
                "ksp"(libs.findLibrary("com-google-dagger-hilt.compiler").get())
            }

            pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
                dependencies {
                    "implementation"(libs.findLibrary("com-google-dagger-hilt.core").get())
                }
            }

            pluginManager.withPlugin("com.android.base") {
                apply(plugin = "dagger.hilt.android.plugin")
                dependencies {
                    add("implementation", libs.findLibrary("com-google-dagger-hilt.android").get())
                }
            }
        }
    }
}