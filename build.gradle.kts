// Top-level build file
plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.gradle.nexus.publish.plugin).apply(false)
}

// مقادیر خالی برای جلوگیری از خطای بیلد در ماژول akari-core
extra["signing.keyId"] = ""
extra["signing.password"] = ""
extra["signing.key"] = ""
extra["centralPortalUsername"] = ""
extra["centralPortalPassword"] = ""
extra["sonatypeStagingProfileId"] = ""

tasks.register("clean") {
    doFirst {
        delete(rootProject.layout.buildDirectory)
    }
}