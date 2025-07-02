import com.android.build.api.dsl.ApplicationExtension
import com.example.hnote.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            plugins.apply("com.android.library")
            plugins.apply("org.jetbrains.kotlin.android")

            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(commonExtension = extension)
        }
    }
}