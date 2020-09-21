package org.atola.internal.suppressUntil

import org.atola.internal.suppressUntil.copiedFromCompiler.BindingContextSuppressCache
import org.atola.internal.suppressUntil.duplicated.PluginConfiguration
import org.atola.internal.suppressUntil.duplicated.SuppressUntilChecker
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
import org.jetbrains.kotlin.psi.KtAnnotated
import org.jetbrains.kotlin.resolve.BindingContext

internal class SuppressUntilSuppressCache(private val config: PluginConfiguration, context: BindingContext) :
    BindingContextSuppressCache(context) {
    override fun getSuppressionAnnotations(annotated: KtAnnotated): List<AnnotationDescriptor> =
        SuppressUntilChecker.GetAdditionalSuppressionAnnotations(
            config,
            super.getSuppressionAnnotations(annotated)
        )
}