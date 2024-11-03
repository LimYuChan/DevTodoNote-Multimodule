import androidx.room.gradle.RoomExtension
import com.note.convention.findLibrary
import com.note.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidRoomConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.run {
                apply("androidx.room")
                apply("com.google.devtools.ksp")
            }

            extensions.configure<RoomExtension> {
                schemaDirectory("$projectDir/schemas")
            }

            dependencies {
                add("implementation", findLibrary("room.runtime"))
                add("implementation", findLibrary("room.ktx"))
                add("implementation", findLibrary("room.paging"))
                add("ksp", findLibrary("room.compiler"))
            }
        }
    }
}