@file:Suppress("DEPRECATION")

package org.atola.internal.suppressUntil

import com.google.auto.service.AutoService
import org.gradle.api.Project
import org.gradle.api.tasks.compile.AbstractCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinGradleSubplugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

@AutoService(KotlinGradleSubplugin::class)
class SuppressUntilGradleSubplugin : KotlinGradleSubplugin<AbstractCompile> {
    override fun isApplicable(project: Project, task: AbstractCompile): Boolean =
        project.plugins.hasPlugin(SuppressUntilGradlePlugin::class.java)

    override fun getCompilerPluginId() = "atolaSuppressUntil"

    override fun getPluginArtifact() =
        SubpluginArtifact("org.atola.internal.suppress-until", "kotlin-plugin", "1.0.0")

    override fun apply(
        project: Project,
        kotlinCompile: AbstractCompile,
        javaCompile: AbstractCompile?,
        variantData: Any?,
        androidProjectHandler: Any?,
        kotlinCompilation: KotlinCompilation<KotlinCommonOptions>?
    ): List<SubpluginOption> {
        val extension = project.extensions.findByType(SuppressUntilPluginExtension::class.java)
            ?: SuppressUntilPluginExtension()

        val enabled = SubpluginOption("enabled", extension.enabled.toString())
        val version = SubpluginOption("productVersion", extension.productVersion)
        val annotation = SubpluginOption("annotation", extension.annotation)
        val versionPropName = SubpluginOption("versionPropName", extension.versionPropName)
        val namesPropName = SubpluginOption("namesPropName", extension.namesPropName)

        return listOf(enabled, version, annotation, versionPropName, namesPropName)
    }
}