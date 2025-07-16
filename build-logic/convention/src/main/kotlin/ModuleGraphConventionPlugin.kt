import com.example.hnotes.libs
import dev.iurysouza.modulegraph.ModuleType
import dev.iurysouza.modulegraph.Orientation
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

            extensions.configure<ModuleGraphExtension> {
                graph(
                    readmePath = "${projectDir}/README.md",
                    heading = "### Module Graph",
                    setupConfig = {
                        showFullPath = true
                        setStyleByModuleType = true
                        orientation = Orientation.LEFT_TO_RIGHT
                        nestingEnabled = true
                        theme = Theme.BASE(
                            themeVariables = mapOf(
                                "primaryTextColor" to "#fff",
                                "primaryColor" to "#5a4f7c",
                                "primaryBorderColor" to "#5a4f7c",
                                "lineColor" to "#f5a623",
                                "tertiaryColor" to "#40375c",
                                "fontSize" to "12px",
                            ),
                            moduleTypes = listOf(
                                ModuleType.AndroidApp(),
                                ModuleType.AndroidLibrary(),
                                ModuleType.Kotlin()
                            ),
                            focusColor = "#FA8140"
                        )
                    }
                )
            }
        }
    }
}
