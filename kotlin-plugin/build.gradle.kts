plugins {
    `java-gradle-plugin`
    kotlin("kapt")
}

publishing {
    publications {
        create<MavenPublication>("kotlin-plugin") {
            from(components["java"])
        }
    }
}

dependencies {
    testImplementation("com.github.smaugfm.kotlin-compile-testing:kotlin-compile-testing:jitpack-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:_")
    implementation("com.google.auto.service:auto-service:_")
    kapt("com.google.auto.service:auto-service:_")
}