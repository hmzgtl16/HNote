import com.example.hnotes.libs
import dev.iurysouza.modulegraph.Theme
import dev.iurysouza.modulegraph.gradle.ModuleGraphExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class ModuleGraphConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("dev-iurysouza-modulegraph").get().get().pluginId)

            val moduleName = displayName
                .substring(startIndex = 8)
                .replace(oldChar = Char(39), newChar = Char(96))

            extensions.configure<ModuleGraphExtension> {
                graph(
                    readmePath = "${projectDir}/README.md",
                    heading = "# $moduleName Module",
                    setupConfig = {
                        showFullPath = true
                        setStyleByModuleType = true
                        nestingEnabled = true
                        theme = Theme.DARK
                    }
                )
            }
        }
    }
}
