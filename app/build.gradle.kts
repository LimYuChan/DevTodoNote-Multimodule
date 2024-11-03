import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.todonote.android.application)
    alias(libs.plugins.navigation.safeargs)
}

android {
    namespace = "com.note.githubtodo"

    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())

    defaultConfig {
        buildConfigField("String", "GITHUB_REDIRECT_CALLBACK", properties.getProperty("githubRedirectCallback"))
    }

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.activity)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    implementation(libs.bundles.navigation)
    implementation(libs.androidx.security.crypto.ktx)
    implementation(libs.androidx.core.splashscreen)

    //Core Module
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.ui)
    implementation(projects.core.navigator)
    implementation(projects.core.featureBase)
    implementation(projects.core.database)

    //Feature Module
    //Auth
    implementation(projects.auth.data)
    implementation(projects.auth.domain)
    implementation(projects.auth.presentation)

    //Home
    implementation(projects.home.data)
    implementation(projects.home.domain)
    implementation(projects.home.presentation)

    //Task
    implementation(projects.note.data)
    implementation(projects.note.domain)
    implementation(projects.note.presentation)
}