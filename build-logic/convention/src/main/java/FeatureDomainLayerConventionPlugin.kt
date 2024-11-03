import org.gradle.api.Plugin
import org.gradle.api.Project

class FeatureDomainLayerConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("todonote.jvm.library")
            dependencies.add("implementation", project(":core:domain"))
        }
    }
}