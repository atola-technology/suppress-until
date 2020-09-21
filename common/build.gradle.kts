publishing {
    publications {
        create<MavenPublication>("common") {
            from(components["java"])
        }
    }
}
