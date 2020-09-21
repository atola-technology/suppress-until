package org.atola.internal.suppressUntil.ceremony

import com.google.auto.service.AutoService
import org.atola.common.Version
import org.atola.internal.suppressUntil.ceremony.SuppressUntilOptions.KEY_ANNOTATION
import org.atola.internal.suppressUntil.ceremony.SuppressUntilOptions.KEY_ENABLED
import org.atola.internal.suppressUntil.ceremony.SuppressUntilOptions.KEY_NAMES_PROPERTY_NAME
import org.atola.internal.suppressUntil.ceremony.SuppressUntilOptions.KEY_PRODUCT_VERSION
import org.atola.internal.suppressUntil.ceremony.SuppressUntilOptions.KEY_VERSION_PROPERTY_NAME
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.name.FqName

@AutoService(CommandLineProcessor::class)
class SuppressUntilCommandLineProcessor : CommandLineProcessor {
    override val pluginId: String
        get() = "atolaSuppressUntil"

    override val pluginOptions = listOf(
        CliOption(
            KEY_ENABLED.toString(),
            "<true|false>",
            "Whether plugin is enabled. Default = true",
            required = false
        ),
        CliOption(
            KEY_PRODUCT_VERSION.toString(),
            "major.minor.micro",
            "Current product version",
            required = true
        ),
        CliOption(
            KEY_ANNOTATION.toString(),
            "fully.qualified.name",
            "Fully qualified name of the annotation class.",
            required = true
        ),
        CliOption(
            KEY_VERSION_PROPERTY_NAME.toString(),
            "string",
            "Name of the property of the annotation class " +
                "which holds version string which to suppress warnings until. Default = version",
            required = false
        ),
        CliOption(
            KEY_NAMES_PROPERTY_NAME.toString(),
            "string",
            "Name of the property of the annotation class " +
                "which holds diagnostics to suppress. Default = names",
            required = false
        ),
    )

    override fun processOption(
        option: AbstractCliOption,
        value: String,
        configuration: CompilerConfiguration
    ) {
        when (option.optionName) {
            KEY_ENABLED.toString() -> configuration.put(KEY_ENABLED, value.toLowerCase().toBoolean())
            KEY_PRODUCT_VERSION.toString() -> configuration.put(KEY_PRODUCT_VERSION, Version.parseVersion(value))
            KEY_ANNOTATION.toString() -> configuration.put(KEY_ANNOTATION, FqName(value))
            KEY_VERSION_PROPERTY_NAME.toString() -> configuration.put(KEY_VERSION_PROPERTY_NAME, value)
            KEY_NAMES_PROPERTY_NAME.toString() -> configuration.put(KEY_NAMES_PROPERTY_NAME, value)
        }
    }
}