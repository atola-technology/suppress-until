package org.atola.internal.suppressUntil

import org.gradle.api.Plugin
import org.gradle.api.Project

class SuppressUntilGradlePlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        extensions.create("suppressUntil", SuppressUntilPluginExtension::class.java)
    }
}