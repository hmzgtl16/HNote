package com.example.hnotes.feature.note

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.hnotes.core.model.Item
import com.example.hnotes.core.model.Reminder
import com.example.hnotes.core.model.RepeatMode
import kotlinx.datetime.Clock
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration.Companion.hours

class NoteScreenTest {

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun noteScreen_emptyScreen() {
        composeTestRule.setContent {
            NoteScreen(
                uiState = NoteUiState(),
                onEvent = {}
            )
        }

        composeTestRule
            .onNodeWithText(
                text = composeTestRule.activity.getString(R.string.feature_note_title_placeholder)
            )
            .assertExists()

        composeTestRule
            .onNodeWithText(
                text = composeTestRule.activity.getString(R.string.feature_note_description_placeholder)
            )
            .assertExists()
    }

    @Test
    fun noteScreen_filledScreen() {
        composeTestRule.setContent {
            NoteScreen(
                uiState = NoteUiState(
                    title = "Note Title",
                    content = "Note description for...",
                    backgroundColor = null,
                    reminder = Reminder(
                        time = Clock.System.now().plus(4.hours),
                        repeatMode = RepeatMode.DAILY
                    ),
                    items = listOf(
                        Item(id = 1L, content = "Item 1", checked = false),
                        Item(id = 2L, content = "Item 2", checked = true),
                        Item(id = 3L, content = "Item 3", checked = false),
                        Item(id = 4L, content = "Item 4", checked = false),
                        Item(id = 5L, content = "Item 5", checked = true)
                    )
                ),
                onEvent = {}
            )
        }

        composeTestRule
            .onNodeWithText(text = "Note Title")
            .assertExists()

        composeTestRule
            .onNodeWithText(text = "Note description for...")
            .assertExists()
    }
}