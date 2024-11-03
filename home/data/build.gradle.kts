plugins {
    alias(libs.plugins.todonote.feature.data)
}

android {
    namespace = "com.note.home.data"
}

dependencies {
    implementation(projects.home.domain)
    implementation(libs.paging.common)
}