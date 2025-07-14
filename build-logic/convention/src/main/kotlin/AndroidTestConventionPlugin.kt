import com.android.build.gradle.TestExtension
import com.example.hnote.configureGradleManagedDevices
import com.example.hnote.configureKotlinAndroid
import com.example.hnote.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidTestConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("com.android.test").get().get().pluginId)
            apply(plugin = libs.findPlugin("org.jetbrains.kotlin.android").get().get().pluginId)

            extensions.configure<TestExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 36
                configureGradleManagedDevices(this)
            }
        }
    }
}