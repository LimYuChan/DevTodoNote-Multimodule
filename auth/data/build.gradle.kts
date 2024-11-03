import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.todonote.feature.data)
}

android {
    namespace = "com.note.auth.data"

    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())

    defaultConfig {
        buildConfigField(type = "String", name = "GITHUB_CLIENT_ID", value = properties.getProperty("githubClientId"))
        buildConfigField(type = "String", name = "GITHUB_SECRET_KEY", value = properties.getProperty("githubSecretKey"))
    }
}

dependencies {
    implementation(projects.auth.domain)
}