package org.atola.internal.suppressUntil.ceremony

import com.google.auto.service.AutoService
import org.atola.internal.suppressUntil.ceremony.SuppressUntilOptions.KEY_ANNOTATION
import org.atola.internal.suppressUntil.ceremony.SuppressUntilOptions.KEY_ENABLED
import org.atola.internal.suppressUntil.ceremony.SuppressUntilOptions.KEY_NAMES_PROPERTY_NAME
import org.atola.internal.suppressUntil.ceremony.SuppressUntilOptions.KEY_PRODUCT_VERSION
import org.atola.internal.suppressUntil.ceremony.SuppressUntilOptions.KEY_VERSION_PROPERTY_NAME
import org.atola.internal.suppressUntil.duplicated.AnnotationConfiguration
import org.atola.internal.suppressUntil.duplicated.PluginConfiguration
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.resolve.jvm.extensions.AnalysisHandlerExtension

@AutoService(ComponentRegistrar::class)
class SuppressUntilComponentRegistrar : ComponentRegistrar {
    private val configurationOverride: PluginConfiguration?

    @Suppress("unused")
    constructor() {
        configurationOverride = null
    }

    internal constructor(pluginConfiguration: PluginConfiguration?) {
        configurationOverride = pluginConfiguration
    }

    override fun registerProjectComponents(project: MockProject, configuration: CompilerConfiguration) {
        if (!configuration.get(KEY_ENABLED, true)) return

        val pluginConfiguration = configurationOverride ?: GetConfigFromCompilerConfiguration(configuration)

        AnalysisHandlerExtension.registerExtension(
            project,
            SuppressUntilAnalysisHandler(pluginConfiguration)
        )
    }

    private fun GetConfigFromCompilerConfiguration(configuration: CompilerConfiguration): PluginConfiguration {
        val productVersion =
            configuration.getNotNull(KEY_PRODUCT_VERSION)

        val annotation = configuration.getNotNull(KEY_ANNOTATION)
        val versionPropName = configuration.getNotNull(KEY_VERSION_PROPERTY_NAME)
        val namesPropName = configuration.getNotNull(KEY_NAMES_PROPERTY_NAME)

        return PluginConfiguration(
            productVersion,
            AnnotationConfiguration(
                annotation,
                versionPropName,
                namesPropName,
            )
        )
    }
}