@file:Suppress("UnstableApiUsage")

package org.atola.internal.suppressUntil.ide

import com.intellij.ide.ApplicationInitializedListener
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ComponentManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.extensions.DefaultPluginDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.serviceContainer.ComponentManagerImpl
import org.jetbrains.kotlin.caches.resolve.KotlinCacheService

/**
 * Грязный хак по подмене KotlinCacheService
 *
 */
class IdeRegistrar : ApplicationInitializedListener {
    fun <T : Any> ComponentManager.registerService(service: Class<T>, instance: T): Unit =
        (this as? ComponentManagerImpl)
            ?.registerServiceInstance(
                service,
                instance,
                DefaultPluginDescriptor("registers service:${service.simpleName}")
            )
            ?: Logger.getInstance("#SuppressUntil").error(
                "Service:${service.simpleName} could not be REGISTERED properly.\n"
            )

    override fun componentsInitialized() {
        ApplicationManager.getApplication()?.let { app ->
            app.messageBus.connect(app).subscribe(
                ProjectManager.TOPIC,
                object : ProjectManagerListener {
                    override fun projectOpened(project: Project) {
                        val serviceCls = KotlinCacheService::class.java
                        project.getService(serviceCls)?.let {
                            project
                                .registerService(
                                    serviceCls,
                                    SuppressUntilKotlinCacheService(project, it)
                                )
                        }
                    }
                }
            )
        }
    }
}