package org.atola.internal.suppressUntil.copiedFromCompiler

import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
import org.jetbrains.kotlin.diagnostics.Diagnostic
import org.jetbrains.kotlin.psi.KtAnnotated
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.diagnostics.DiagnosticSuppressor
import org.jetbrains.kotlin.resolve.diagnostics.KotlinSuppressCache

/**
 * Этот класс скопирован из репозитория kotlin
 * [org.jetbrains.kotlin.resolve.diagnostics.BindingContextSuppressCache]
 * Версия 1.4.10
 */
internal open class BindingContextSuppressCache(val context: BindingContext) : KotlinSuppressCache() {
    override fun getSuppressionAnnotations(annotated: KtAnnotated): List<AnnotationDescriptor> {
        val descriptor = context.get(BindingContext.DECLARATION_TO_DESCRIPTOR, annotated)

        return if (descriptor != null) {
            descriptor.annotations.toList()
        } else {
            annotated.annotationEntries.mapNotNull { context.get(BindingContext.ANNOTATION, it) }
        }
    }

    override fun isSuppressedByExtension(suppressor: DiagnosticSuppressor, diagnostic: Diagnostic): Boolean {
        return suppressor.isSuppressed(diagnostic, context)
    }
}