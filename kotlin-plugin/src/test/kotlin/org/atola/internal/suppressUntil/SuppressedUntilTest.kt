package org.atola.internal.suppressUntil

import org.junit.jupiter.api.Test

class SuppressedUntilTest {
    @Test
    fun `Suppressing warnings with various scopes`() {
        WarningSuppressedChecker(TestVars.differentScopes)
            .apply {
                presentCondition = { warningPresentNTimes(it, 3) }
            }
            .noWarningsAlsoWithoutPlugin()

        WarningSuppressedChecker(TestVars.differentScopes)
            .noWarningsAlsoWithoutPlugin()
    }

    @Test
    fun `Not suppressing on expired version`() {
        WarningSuppressedChecker(TestVars.wholeFile)
            .apply {
                version = TestVars.expiredVersion
            }.hasWarnings()
    }
}