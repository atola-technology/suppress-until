package org.atola.internal.suppressUntil.ceremony

import org.atola.internal.suppressUntil.SuppressUntilSuppressCache
import org.atola.internal.suppressUntil.duplicated.PluginConfiguration
import org.jetbrains.kotlin.analyzer.AnalysisResult
import org.jetbrains.kotlin.com.intellij.openapi.project.Project
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.diagnostics.Diagnostic
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.resolve.BindingTrace
import org.jetbrains.kotlin.resolve.BindingTraceContext
import org.jetbrains.kotlin.resolve.diagnostics.KotlinSuppressCache
import org.jetbrains.kotlin.resolve.diagnostics.MutableDiagnosticsWithSuppression
import org.jetbrains.kotlin.resolve.jvm.extensions.AnalysisHandlerExtension

internal class SuppressUntilAnalysisHandler(val pluginConfiguration: PluginConfiguration) : AnalysisHandlerExtension {

    override fun analysisCompleted(
        project: Project,
        module: ModuleDescriptor,
        bindingTrace: BindingTrace,
        files: Collection<KtFile>
    ): AnalysisResult? {
        val suppressCache = SuppressUntilSuppressCache(pluginConfiguration, bindingTrace.bindingContext)

        DeleteDiagnosticsUsingReflection(bindingTrace, suppressCache)

        return null
    }

    private fun DeleteDiagnosticsUsingReflection(
        bindingTrace: BindingTrace,
        suppressCache: KotlinSuppressCache,
    ) {
        val diagnostics: MutableDiagnosticsWithSuppression =
            BindingTraceContext::class.java.getDeclaredField("mutableDiagnostics").also { it.isAccessible = true }
                .get(bindingTrace) as MutableDiagnosticsWithSuppression

        val mutableDiagnostics = diagnostics.getOwnDiagnostics() as ArrayList<Diagnostic>

        mutableDiagnostics.removeIf { suppressCache.isSuppressed(it) }
    }
}