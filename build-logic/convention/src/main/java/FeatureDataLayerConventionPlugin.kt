import com.note.convention.findLibrary
import com.note.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class FeatureDataLayerConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("todonote.android.library")
                apply("todonote.android.hilt")
            }

            dependencies {
                add("implementation", project(":core:data"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":core:common"))
                add("implementation", findLibrary("retrofit.core"))
            }
        }
    }
}