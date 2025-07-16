import com.example.hnotes.libs
import com.jraska.module.graph.assertion.GraphRulesExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class ModuleGraphConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("com-jraska-modulegraph").get().get().pluginId)

            extensions.configure<GraphRulesExtension> {
                maxHeight = 4
                allowed = arrayOf(":.* -> :core", ":feature.* -> :lib.*")
                restricted = arrayOf(":feature-[a-z]* -X> :forbidden-to-depend-on")
                configurations = setOf("api", "implementation")
                assertOnAnyBuild = false
            }
        }
    }
}
