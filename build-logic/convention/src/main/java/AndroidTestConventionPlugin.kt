import com.note.convention.findLibrary
import com.note.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidTestConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("testImplementation", findLibrary("junit"))
                add("androidTestImplementation", findLibrary("androidx.junit"))
                add("androidTestImplementation", findLibrary("androidx.espresso.core"))
            }
        }
    }
}