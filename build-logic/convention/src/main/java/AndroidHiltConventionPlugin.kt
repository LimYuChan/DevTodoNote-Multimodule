import com.note.convention.findLibrary
import com.note.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("dagger.hilt.android.plugin")
            }
            dependencies {
                add("implementation", findLibrary("hilt.android"))
                add("ksp", findLibrary("hilt.android.compiler"))
            }
        }
    }
}