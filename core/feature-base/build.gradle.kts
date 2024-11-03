plugins {
    alias(libs.plugins.todonote.android.library)
    alias(libs.plugins.todonote.android.hilt)
}

android {
    namespace = "com.note.feature_base"

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(projects.core.navigator)
}