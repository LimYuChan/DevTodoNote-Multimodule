import com.android.build.api.dsl.LibraryExtension
import com.note.convention.findLibrary
import com.note.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class FeaturePresentationLayerConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("todonote.android.library")
                apply("todonote.android.hilt")
                apply("androidx.navigation.safeargs.kotlin")
            }
            extensions.configure<LibraryExtension> {
                buildFeatures.dataBinding = true
                buildFeatures.viewBinding = true
            }
            dependencies {
                add("implementation", findLibrary("androidx.core.ktx"))
                add("implementation", findLibrary("androidx.appcompat"))
                add("implementation", findLibrary("material"))
                add("implementation", findLibrary("androidx.constraintlayout"))
                add("implementation", findLibrary("androidx.fragment.ktx"))
                add("implementation", findLibrary("androidx.lifecycle.viewmodel.ktx"))
                add("implementation", findLibrary("androidx.lifecycle.viewmodel.savedstate"))

                add("implementation", libs.findBundle("navigation").get())

                add("implementation", project(":core:ui"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":core:common"))
                add("implementation", project(":core:feature-base"))
                add("implementation", project(":core:navigator"))
            }
        }
    }
}