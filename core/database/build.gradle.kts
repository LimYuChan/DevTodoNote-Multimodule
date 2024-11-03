plugins {
    alias(libs.plugins.todonote.android.library)
    alias(libs.plugins.todonote.android.room)
    alias(libs.plugins.todonote.android.hilt)
}

android {
    namespace = "com.note.core.database"
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.paging.common)
}