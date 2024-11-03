package com.note.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.konan.properties.Properties


internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType
) {
    with(commonExtension) {
        buildFeatures {
            buildConfig = true
        }

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension>() {
                    buildTypes {
                        debug {
                            configureDebugBuildType(properties)
                            applicationIdSuffix = FlavorType.DEV.idSuffix
                        }
                        create(FlavorTypeName.STG) {
                            configureStageBuildType(properties)
                            applicationIdSuffix = FlavorType.STG.idSuffix
                        }
                        release {
                            configureProductionBuildType(commonExtension, properties)
                        }
                    }
                }
            }
            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(properties)
                        }
                        create(FlavorTypeName.STG) {
                            configureStageBuildType(properties)
                        }
                        release {
                            configureProductionBuildType(commonExtension, properties)
                        }
                    }
                }
            }
        }
    }
}

/**
 * 각 Flavor 마다 각각 다르게 들어가야하는 프로퍼티 정의
 * */
private fun BuildType.configureDebugBuildType(properties: Properties) {
}

private fun BuildType.configureStageBuildType(properties: Properties) {
}

private fun BuildType.configureProductionBuildType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    properties: Properties
) {
    /** 배포 파일 최적화 */
    isMinifyEnabled = true //코드 축소 활성화

    /** 난독화 활성화 */
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}