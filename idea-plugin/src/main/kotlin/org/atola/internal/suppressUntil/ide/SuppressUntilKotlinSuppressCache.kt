package org.atola.internal.suppressUntil.ide

import com.intellij.openapi.project.Project
import org.atola.internal.suppressUntil.config.SuppressUntilConfigStorage
import org.atola.internal.suppressUntil.duplicated.SuppressUntilChecker
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
import org.jetbrains.kotlin.idea.caches.resolve.analyze
import org.jetbrains.kotlin.psi.KtAnnotated
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtModifierListOwner
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.diagnostics.KotlinSuppressCache
import org.jetbrains.kotlin.resolve.lazy.BodyResolveMode

internal class SuppressUntilKotlinSuppressCache(
    val project: Project,
    val baseImplementation: KotlinSuppressCache,
) : KotlinSuppressCache() {
    override fun getSuppressionAnnotations(annotated: KtAnnotated): List<AnnotationDescriptor> =
        SuppressUntilConfigStorage.instance(project)
            .PluginConfig
            ?.let {
                SuppressUntilChecker.GetAdditionalSuppressionAnnotations(
                    it,
                    getSuppressionAnnotationsCopied(it.Annotation.FqName.shortName().toString(), annotated)
                )
            } ?: baseImplementation.getSuppressionAnnotations(annotated)

    /**
     * Этот метод скопирован из компилятора kotlin
     * из класса [org.jetbrains.kotlin.idea.caches.resolve.KotlinCacheServiceImpl]
     * Версия 1.4.10
     */
    private fun getSuppressionAnnotationsCopied(
        customAnnotationShortName: String,
        annotated: KtAnnotated
    ): List<AnnotationDescriptor> {
        if (annotated.annotationEntries.none {
            it.calleeExpression?.text?.endsWith(customAnnotationShortName) == true
        }
        ) {
            return emptyList()
        }

        val context =
            when (annotated) {
                is KtFile -> {
                    annotated.fileAnnotationList?.analyze(BodyResolveMode.PARTIAL)
                        ?: return emptyList()
                }
                is KtModifierListOwner -> {
                    annotated.modifierList?.analyze(BodyResolveMode.PARTIAL)
                        ?: return emptyList()
                }
                else ->
                    annotated.analyze(BodyResolveMode.PARTIAL)
            }

        val annotatedDescriptor = context.get(BindingContext.DECLARATION_TO_DESCRIPTOR, annotated)

        if (annotatedDescriptor != null) {
            return annotatedDescriptor.annotations.toList()
        }

        return annotated.annotationEntries.mapNotNull {
            context.get(
                BindingContext.ANNOTATION,
                it
            )
        }
    }
}