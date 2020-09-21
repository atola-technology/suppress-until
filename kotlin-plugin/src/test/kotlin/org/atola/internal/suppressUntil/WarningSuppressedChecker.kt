package org.atola.internal.suppressUntil

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.atola.internal.suppressUntil.ceremony.SuppressUntilComponentRegistrar
import org.atola.internal.suppressUntil.duplicated.AnnotationConfiguration
import org.atola.internal.suppressUntil.duplicated.PluginConfiguration
import org.jetbrains.kotlin.name.FqName

internal val annotationConfiguration =
    AnnotationConfiguration(
        FqName(SuppressUntilTestAnnotation::class.qualifiedName.toString()),
        SuppressUntilTestAnnotation::versionTestProp.name,
        SuppressUntilTestAnnotation::namesTestProp.name,
    )

@Suppress
internal class WarningSuppressedChecker(
    val source: SourceFile
) {
    var presentCondition: (KotlinCompilation.Result) -> Boolean = { warningPresentNTimes(it, 1) }
    var notPresent: (KotlinCompilation.Result) -> Boolean = { warningPresentNTimes(it, 0) }
    var version = TestVars.passingVersion

    private var usePlugin = true

    private fun compile() = KotlinCompilation().apply {
        allWarningsAsErrors = false
        sources = listOf(source)
        compilerPlugins =
            if (usePlugin)
                listOf(
                    SuppressUntilComponentRegistrar(
                        PluginConfiguration(version, annotationConfiguration)
                    )
                )
            else
                emptyList()
        inheritClassPath = true
        kotlincArguments = listOf()
        verbose = false
    }.compile().also {
        assert(KotlinCompilation.ExitCode.OK == it.exitCode)
    }

    private fun check(forward: Boolean) {
        val result = compile()

        val assert: (Boolean) -> Unit = if (forward) {
            (
                {
                    assert(it)
                }
                )
        } else {
            (
                {
                    assert(!it)
                }
                )
        }

        assert(
            if (usePlugin)
                notPresent(result)
            else
                presentCondition(result)
        )
    }

    fun noWarnings() {
        check(true)
    }

    fun hasWarnings() {
        check(false)
    }

    fun noWarningsAlsoWithoutPlugin() {
        usePlugin = true
        noWarnings()
        usePlugin = false
        noWarnings()
    }
}