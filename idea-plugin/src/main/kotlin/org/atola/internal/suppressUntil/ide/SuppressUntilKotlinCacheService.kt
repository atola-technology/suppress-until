package org.atola.internal.suppressUntil.ide

import com.intellij.openapi.project.Project
import com.intellij.psi.util.CachedValue
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.intellij.psi.util.PsiModificationTracker
import org.jetbrains.kotlin.caches.resolve.KotlinCacheService
import org.jetbrains.kotlin.idea.caches.project.LibraryModificationTracker
import org.jetbrains.kotlin.resolve.diagnostics.KotlinSuppressCache

internal class SuppressUntilKotlinCacheService(
    project: Project,
    val delegate: KotlinCacheService
) :
    KotlinCacheService by delegate {
    private val suppressCache: CachedValue<KotlinSuppressCache> =
        CachedValuesManager.getManager(project).createCachedValue(
            {
                CachedValueProvider.Result(
                    SuppressUntilKotlinSuppressCache(project, delegate.getSuppressionCache()),
                    LibraryModificationTracker.getInstance(project),
                    PsiModificationTracker.MODIFICATION_COUNT
                )
            },
            false
        )

    override fun getSuppressionCache(): KotlinSuppressCache = suppressCache.value
}