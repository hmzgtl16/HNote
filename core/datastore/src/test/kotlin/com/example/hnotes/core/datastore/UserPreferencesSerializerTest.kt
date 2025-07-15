package com.example.hnotes.core.datastore

import androidx.datastore.core.CorruptionException
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.test.assertEquals

class UserPreferencesSerializerTest {

    @Test
    fun defaultUserPreferences_isEmpty() {
        val serializer = UserPreferencesSerializer()
        assertEquals(
            expected = userPreferences { },
            actual = serializer.defaultValue
        )
    }

    @Test
    fun writingAndReadingUserPreferences_outputsCorrectValue() = runTest {
        val serializer = UserPreferencesSerializer()

        val expected = userPreferences {
            uiThemeConfig = UiThemeConfigProto.UI_THEME_CONFIG_FOLLOW_SYSTEM
            useDynamicUiTheme = true
        }

        val outputStream = ByteArrayOutputStream()

        expected.writeTo(outputStream)

        val inputStream = ByteArrayInputStream(outputStream.toByteArray())

        val actual = serializer.readFrom(input = inputStream)

        assertEquals(expected = expected, actual = actual)
    }

    @Test(expected = CorruptionException::class)
    fun readingInvalidUserPreferences_throwsCorruptionException() = runTest {
        val serializer = UserPreferencesSerializer()

        serializer.readFrom(ByteArrayInputStream(byteArrayOf(0)))
    }
}