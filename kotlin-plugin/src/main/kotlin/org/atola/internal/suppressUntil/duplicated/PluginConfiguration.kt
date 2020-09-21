package org.atola.internal.suppressUntil.duplicated

import org.atola.common.Version
import org.jetbrains.kotlin.name.FqName

/**
 * Этот файл совпадает с файлом из gradle-проекта :idea-plugin.
 *
 * Так пришлось сделать из-за того что классы из пакета [org.jetbrains.kotlin] подтягиваются из разных jar-ников
 * в проекте :idea-plugin и в проекте :kotlin-plugin.
 */

internal data class AnnotationConfiguration(
    val FqName: FqName,
    val VersionPropertyName: String,
    val NamesPropertyName: String,
)

internal data class PluginConfiguration(
    val ProductVersion: Version,
    val Annotation: AnnotationConfiguration,
)