import com.example.hnote.configureKotlinJvm
import com.example.hnote.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("org.jetbrains.kotlin.jvm").get().get().pluginId)

            configureKotlinJvm()
            dependencies {
                add("testImplementation", libs.findLibrary("org.jetbrains.kotlin.test").get())
            }
        }
    }
}