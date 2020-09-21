package org.atola.internal.suppressUntil.config

import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.project.Project
import javax.swing.JComponent

class SuppressUntilConfig(val project: Project) : SearchableConfigurable {
    private val suppressUntilConfigStorage: SuppressUntilConfigStorage = SuppressUntilConfigStorage.instance(project)
    private val suppressUntilConfigurationForm: SuppressUntilConfigurationForm = SuppressUntilConfigurationForm()

    override fun isModified() = suppressUntilConfigurationForm.isModified

    override fun getId(): String = "org.atola.internal.suppressUntil.config"

    override fun getDisplayName() = "Suppress Until"

    override fun apply() {
        suppressUntilConfigurationForm.apply()
        DaemonCodeAnalyzer.getInstance(project).restart()
    }

    override fun createComponent(): JComponent? =
        suppressUntilConfigurationForm.createPanel(suppressUntilConfigStorage)

    override fun reset() {
        suppressUntilConfigurationForm.reset()
    }
}