plugins {
    alias(libs.plugins.todonote.feature.presentation)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.note.note.presentation"
}

dependencies {
    implementation(projects.note.domain)
    implementation(libs.image.picker)
    implementation(libs.glide)
    implementation(libs.swiperefreshlayout)
    implementation(libs.paging.runtime)
    implementation(libs.kotlinx.serialization.json)
}