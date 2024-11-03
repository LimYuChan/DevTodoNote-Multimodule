import com.android.build.api.dsl.ApplicationExtension
import com.note.convention.ExtensionType
import com.note.convention.configureBuildTypes
import com.note.convention.configureKotlinAndroid
import com.note.convention.findVersion
import com.note.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("todonote.android.hilt")
                apply("todonote.android.test")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.APPLICATION
                )

                defaultConfig {
                    applicationId = findVersion("projectApplicationId")

                    targetSdk = findVersion("projectTargetSdkVersion").toInt()

                    versionCode = findVersion("projectVersionCode").toInt()
                    versionName = findVersion("projectVersionName")

                }
            }
        }
    }
}