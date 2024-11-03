import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "todonote.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "todonote.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidHilt") {
            id = "todonote.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("jvmLibrary") {
            id = "todonote.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("androidTest") {
            id = "todonote.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("androidRoom") {
            id = "todonote.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("featureData") {
            id = "todonote.feature.data"
            implementationClass = "FeatureDataLayerConventionPlugin"
        }
        register("featureDoamin"){
            id = "todonote.feature.domain"
            implementationClass = "FeatureDomainLayerConventionPlugin"
        }
        register("featurePresentation") {
            id = "todonote.feature.presentation"
            implementationClass = "FeaturePresentationLayerConventionPlugin"
        }
    }
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}