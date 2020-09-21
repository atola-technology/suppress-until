package org.atola.internal.suppressUntil

import com.google.common.io.Resources
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.atola.common.Version
import java.io.File
import java.nio.file.Paths

internal object TestVars {
    val passingVersion = Version.parseVersion("2020.7.1")
    val expiredVersion = Version.parseVersion("2020.12")

    val wholeFile = source("WholeFile.kt")
    val differentScopes = source("DifferentScopes.kt")
}

fun String.containsN(target: String): Int {
    var cur = this
    var count = 0
    while (cur.contains(target)) {
        cur = cur.replaceFirst(target, "")
        count++
    }

    return count
}

internal fun warningPresentNTimes(result: KotlinCompilation.Result, times: Int = 1): Boolean {
    require(times >= 0)
    return when (times) {
        0 -> result.messages.contains("Variable 'vasa' is never used").not()
        1 -> result.messages.contains("Variable 'vasa' is never used")
        else -> result.messages.containsN("Variable 'vasa' is never used") == times
    }
}

@Suppress("UnstableApiUsage")
internal fun source(resource: String) =
    SourceFile.fromPath(
        File(Paths.get(Resources.getResource(resource).toURI()).toAbsolutePath().toString())
    )