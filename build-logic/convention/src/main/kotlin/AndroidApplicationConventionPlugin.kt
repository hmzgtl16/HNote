import com.android.build.api.dsl.ApplicationExtension
import com.example.hnotes.configureGradleManagedDevices
import com.example.hnotes.configureKotlinAndroid
import com.example.hnotes.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("com.android.application").get().get().pluginId)
            apply(plugin = libs.findPlugin("org.jetbrains.kotlin.android").get().get().pluginId)

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 36
                testOptions.animationsDisabled = true
                configureGradleManagedDevices(this)
            }
        }
    }
}