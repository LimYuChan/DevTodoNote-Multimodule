package com.note.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.findVersion(versionName: String): String {
    return libs.findVersion(versionName).get().toString()
}

fun Project.findLibrary(libraryName: String): Any {
    return libs.findLibrary(libraryName).get()
}