package org.atola.internal.suppressUntil.duplicated

import org.atola.common.Version
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.constants.StringValue

/**
 * Этот файл совпадает с файлом из gradle-проекта :kotlin-plugin.
 *
 * Так пришлось сделать из-за того что классы из пакета [org.jetbrains.kotlin] подтягиваются из разных jar-ников
 * в проекте :idea-plugin и в проекте :kotlin-plugin.
 */

internal object SuppressUntilChecker {
    private val standardSuppressAnnotationName = FqName("kotlin.Suppress")

    fun GetAdditionalSuppressionAnnotations(
        config: PluginConfiguration,
        baseSuppressionAnnotations: List<AnnotationDescriptor>
    ): List<AnnotationDescriptor> =
        baseSuppressionAnnotations
            .filter { it.fqName == config.Annotation.FqName }
            .filter {
                config.ProductVersion <
                    (ExtractVersionFromAnnotation(config, it) ?: return@filter false)
            }
            .map { MockStandardSuppressAnnotation(config, it) }

    private fun ExtractVersionFromAnnotation(
        config: PluginConfiguration,
        descriptor: AnnotationDescriptor
    ): Version? {
        val versionStr = descriptor
            .allValueArguments
            .map { (key, value) -> Pair(key.identifier, value) }
            .toMap()
            .getOrDefault(
                config.Annotation.VersionPropertyName,
                null
            ) as? StringValue
            ?: return null

        return Version.parseVersionSafe(versionStr.value)
    }

    private fun MockStandardSuppressAnnotation(
        config: PluginConfiguration,
        descriptor: AnnotationDescriptor
    ) =
        object : AnnotationDescriptor by descriptor {
            override val fqName = standardSuppressAnnotationName
            override val allValueArguments =
                descriptor.allValueArguments.filterKeys {
                    it.toString() == config.Annotation.NamesPropertyName
                }
        }
}