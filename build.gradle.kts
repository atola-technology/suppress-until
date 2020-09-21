import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val pluginGroup by extra("org.atola.internal.suppress-until")
val pluginVersion by extra("1.0.0")

repositories {
    mavenCentral()
    jcenter()
    maven("https://www.jitpack.io")
}

plugins {
    kotlin("jvm")
    `maven-publish`
    id("io.gitlab.arturbosch.detekt")
    id("org.jlleitschuh.gradle.ktlint")
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "maven-publish")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    group = pluginGroup
    version = pluginVersion

    repositories {
        mavenCentral()
        jcenter()
        maven("https://www.jitpack.io")
    }

    ktlint {
        enableExperimentalRules.set(true)
        disabledRules.set(
            setOf(
                "experimental:multiline-if-else",
                "experimental:no-empty-first-line-in-method-block",
                "experimental:enum-entry-name-case",
                "experimental:no-empty-first-line-in-method-block"
            )
        )
    }

    detekt {
        failFast = false
        parallel = true
        autoCorrect = false
        buildUponDefaultConfig = true
        config = files("$rootDir/detekt.yml")
        reports {
            html.enabled = true
            xml.enabled = false
            txt.enabled = false
        }
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }

        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }

        test {
            useJUnitPlatform()
        }
    }

    dependencies {
        if (project.name != "common")
            implementation(project(":common"))

        testImplementation("org.junit.jupiter:junit-jupiter-api:_")
        testImplementation("org.junit.jupiter:junit-jupiter-params:_")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:_")
    }
}