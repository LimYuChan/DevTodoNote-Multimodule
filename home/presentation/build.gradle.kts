plugins {
    alias(libs.plugins.todonote.feature.presentation)
}

android {
    namespace = "com.note.home.presentation"
}

dependencies {
    implementation(projects.home.domain)
    implementation(libs.paging.runtime)
    implementation(libs.swiperefreshlayout)
}