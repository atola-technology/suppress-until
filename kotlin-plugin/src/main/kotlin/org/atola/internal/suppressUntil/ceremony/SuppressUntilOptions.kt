package org.atola.internal.suppressUntil.ceremony

import org.atola.common.Version
import org.jetbrains.kotlin.config.CompilerConfigurationKey
import org.jetbrains.kotlin.name.FqName

object SuppressUntilOptions {
    val KEY_ENABLED = CompilerConfigurationKey.create<Boolean>("enabled")
    val KEY_PRODUCT_VERSION = CompilerConfigurationKey.create<Version>("productVersion")
    val KEY_ANNOTATION = CompilerConfigurationKey.create<FqName>("annotation")
    val KEY_VERSION_PROPERTY_NAME = CompilerConfigurationKey.create<String>("versionPropName")
    val KEY_NAMES_PROPERTY_NAME = CompilerConfigurationKey.create<String>("namesPropName")
}