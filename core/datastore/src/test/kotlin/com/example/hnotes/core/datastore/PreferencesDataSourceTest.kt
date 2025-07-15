package com.example.hnotes.core.datastore

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PreferencesDataSourceTest {

    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var subject: PreferencesDataSource

    @Before
    fun setup() {
        subject = PreferencesDataSource(
            userPreferences = InMemoryDataStore(UserPreferences.getDefaultInstance())
        )
    }

    @Test
    fun shouldThemeIsFollowSystemByDefault() = testScope.runTest {
        assertEquals(expected = Theme.FOLLOW_SYSTEM, actual = subject.userData.first().theme)
    }

    @Test
    fun userShouldThemeIsDarkWhenSet() = testScope.runTest {
        subject.setTheme(theme = Theme.DARK)
        assertEquals(expected = Theme.DARK, actual = subject.userData.first().theme)
    }

    @Test
    fun shouldUseDynamicColorFalseByDefault() = testScope.runTest {
        assertFalse(actual = subject.userData.first().useDynamicColor)
    }

    @Test
    fun userShouldUseDynamicColorIsTrueWhenSet() = testScope.runTest {
        subject.setDynamicColor(useDynamicColor = true)
        assertTrue(actual = subject.userData.first().useDynamicColor)
    }
}