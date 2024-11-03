plugins {
    alias(libs.plugins.todonote.feature.data)
}

android {
    namespace = "com.note.note.data"
}

dependencies {
    implementation(projects.core.database)
    implementation(projects.note.domain)
    implementation(libs.paging.common)
    implementation(libs.jsoup)
}