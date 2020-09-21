plugins {
    kotlin("jvm")
    id("org.jetbrains.intellij")
}

val pluginVersion: String by rootProject.extra

intellij {
    pluginName = "Suppress Until"
    version = "2020.2.1"
    type = "IC"
    downloadSources = true
    setPlugins("IntelliLang", "Kotlin", "java")
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }
    patchPluginXml {
        version(pluginVersion)
    }

    publishPlugin {
        dependsOn("patchChangelog")
        token(System.getenv("PUBLISH_TOKEN"))
        channels(pluginVersion.split('-').getOrElse(1) { "default" }.split('.').first())
    }
}