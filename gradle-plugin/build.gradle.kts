plugins {
    `java-gradle-plugin`
    id("com.gradle.plugin-publish")
    kotlin("kapt")
}

gradlePlugin {
    plugins {
        create("suppressUntilPlugin") {
            id = "org.atola.internal.suppress-until"
            displayName = "Suppress Until Annotation Gradle Plugin"
            description = "Enables custom @SuppressUntil annotation which allows selectively suppressing code issues."
            implementationClass = "org.atola.internal.suppressUntil.SuppressUntilGradlePlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("gradle-plugin") {
            from(components["java"])
        }
    }
}

pluginBundle {
    website = "https://atola.com"
    vcsUrl = "https://atola.com"
    tags = listOf("suppress-until", "atola")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin-api:_")
    implementation("com.google.auto.service:auto-service:_")
    kapt("com.google.auto.service:auto-service:_")
}