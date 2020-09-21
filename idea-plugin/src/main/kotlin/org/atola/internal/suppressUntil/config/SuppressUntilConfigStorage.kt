package org.atola.internal.suppressUntil.config

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.annotations.Tag
import org.atola.common.Version
import org.atola.internal.suppressUntil.duplicated.AnnotationConfiguration
import org.atola.internal.suppressUntil.duplicated.PluginConfiguration
import org.jetbrains.kotlin.name.FqName

@State(
    name = "SuppressUntilProjectConfiguration",
    storages = [Storage("suppressUntil.xml")]
)
class SuppressUntilConfigStorage : PersistentStateComponent<SuppressUntilConfigStorage> {
    @Tag
    var enable: Boolean = false

    @Tag
    var productVersion: String = "0.0.0"

    @Tag
    var annotationFqName: String = "SuppressUntil"

    @Tag
    var annotationVersionPropName: String = "version"

    @Tag
    var annotationNamesPropName: String = "names"

    override fun getState(): SuppressUntilConfigStorage? = this

    var PluginConfig: PluginConfiguration? = null
        private set

    override fun loadState(state: SuppressUntilConfigStorage) {
        this.enable = state.enable
        this.productVersion = state.productVersion
        this.annotationFqName = state.annotationFqName
        this.annotationVersionPropName = state.annotationVersionPropName
        this.annotationNamesPropName = state.annotationNamesPropName

        UpdatePluginConfiguration()
    }

    fun UpdatePluginConfiguration() {
        PluginConfig =
            PluginConfiguration(
                Version.parseVersionSafe(productVersion) ?: Version.emptyVersion,
                AnnotationConfiguration(
                    FqName(annotationFqName),
                    annotationVersionPropName,
                    annotationNamesPropName
                )
            ).takeIf { this.enable }
    }

    companion object {
        fun instance(project: Project): SuppressUntilConfigStorage =
            ServiceManager.getService(project, SuppressUntilConfigStorage::class.java)
    }
}