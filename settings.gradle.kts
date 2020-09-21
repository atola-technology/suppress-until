import de.fayard.refreshVersions.bootstrapRefreshVersions

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        jcenter()
    }
}

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("de.fayard.refreshVersions:refreshVersions:0.9.5")
    }
}

bootstrapRefreshVersions()

include(":gradle-plugin")
include(":kotlin-plugin")
include(":idea-plugin")
include(":common")