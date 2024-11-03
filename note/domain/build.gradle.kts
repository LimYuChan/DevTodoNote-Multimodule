plugins {
    alias(libs.plugins.todonote.feature.domain)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.paging.common)
    implementation(libs.kotlinx.serialization.json)
}