import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.todonote.android.library)
    alias(libs.plugins.todonote.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.note.core.data"

    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())

    defaultConfig {
        buildConfigField(type = "String", name = "GITHUB_CLIENT_ID", value = properties.getProperty("githubClientId"))
        buildConfigField(type = "String", name = "GITHUB_SECRET_KEY", value = properties.getProperty("githubSecretKey"))
        buildConfigField(type = "String", name = "BASE_URL", value = properties.getProperty("baseUrl"))
        buildConfigField(type = "String", name = "API_HOST", value = properties.getProperty("apiHost"))
        buildConfigField(type = "String", name = "AUTH_HOST", value = properties.getProperty("authApiHost"))
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.paging.common)
    implementation(libs.bundles.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.data.store)
}