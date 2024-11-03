plugins {
    alias(libs.plugins.todonote.android.library)
    id("kotlin-parcelize")
}

android {
    namespace = "com.note.core.ui"

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(libs.androidx.activity)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.paging.runtime)
    implementation(projects.core.domain)
    implementation(libs.bundles.navigation)
}