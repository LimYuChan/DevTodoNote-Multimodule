import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.todonote.feature.presentation)
}

android {
    namespace = "com.note.auth.presentation"

    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())

    defaultConfig {
        buildConfigField(type = "String", name = "GITHUB_CLIENT_ID", value = properties.getProperty("githubClientId"))
        buildConfigField(type = "String", name = "AUTH_HOST", value = properties.getProperty("authApiHost"))
    }
}

dependencies {
    implementation(projects.auth.domain)
    implementation(libs.browser)
}