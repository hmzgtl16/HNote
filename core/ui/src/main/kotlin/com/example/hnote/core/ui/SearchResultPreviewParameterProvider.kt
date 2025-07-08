package com.example.hnote.core.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.hnote.core.model.Item
import com.example.hnote.core.model.Note
import com.example.hnote.core.model.NoteType
import com.example.hnote.core.model.Reminder
import com.example.hnote.core.model.RepeatMode
import com.example.hnote.core.model.SearchResult
import kotlinx.datetime.Instant

class SearchResultPreviewParameterProvider : PreviewParameterProvider<SearchResult> {

    override val values: Sequence<SearchResult> = sequenceOf(
        SearchResult(
            notes = listOf(
                Note(
                    id = 1L,
                    title = "Note 1",
                    content = "Content of note 1",
                    backgroundColor = -8972498,
                    type = NoteType.SIMPLE,
                    pinned = true
                ),
                Note(
                    id = 2L,
                    title = "Note 2",
                    content = "Content of note 2",
                    type = NoteType.SIMPLE
                ),
                Note(
                    id = 3L,
                    title = "Note 3",
                    content = "Content of note 3",
                    type = NoteType.SIMPLE
                ),
                Note(
                    id = 4L,
                    title = "Note 4",
                    content = "Content of note 4",
                    backgroundColor = -8972498,
                    type = NoteType.SIMPLE
                ),
                Note(
                    id = 5L,
                    title = "Note 5",
                    content = "Content of note 5",
                    type = NoteType.REMINDER,
                    pinned = true,
                    reminder = Reminder(
                        time = Instant.parse("2024-10-01T12:00:00Z"),
                        repeatMode = RepeatMode.NONE,
                        completed = false
                    )
                ),
                Note(
                    id = 6L,
                    title = "Note 6",
                    content = "Content of note 6",
                    type = NoteType.REMINDER,
                    pinned = true,
                    reminder = Reminder(
                        time = Instant.parse("2024-10-01T12:00:00Z"),
                        repeatMode = RepeatMode.DAILY,
                        completed = true
                    )
                ),
                Note(
                    id = 7L,
                    title = "Note 7",
                    content = "Content of note 7",
                    type = NoteType.REMINDER,
                    pinned = true,
                    items = listOf(
                        Item(
                            id = 1L,
                            content = "Item 1",
                            checked = false
                        ),
                        Item(
                            id = 2L,
                            content = "Item 2",
                            checked = true
                        ),
                        Item(
                            id = 3L,
                            content = "Item 3",
                            checked = true
                        )
                    )
                ),
                Note(
                    id = 8L,
                    title = "Note 8",
                    content = "Content of note 8",
                    type = NoteType.REMINDER,
                    pinned = true,
                    items = listOf(
                        Item(
                            id = 4L,
                            content = "Item 4",
                            checked = false
                        ),
                        Item(
                            id = 5L,
                            content = "Item 5",
                            checked = true
                        ),
                        Item(
                            id = 6L,
                            content = "Item 6",
                            checked = false
                        )
                    )
                ),
                Note(
                    id = 9L,
                    title = "Note 9",
                    content = "Content of note 9",
                    type = NoteType.SIMPLE,
                    pinned = false
                ),
                Note(
                    id = 10L,
                    title = "Note 10",
                    content = "Content of note 10",
                    type = NoteType.SIMPLE,
                    pinned = false
                ),
                Note(
                    id = 11L,
                    title = "Note 11",
                    content = "Content of note 11",
                    type = NoteType.SIMPLE,
                    pinned = false,
                    reminder = Reminder(
                        time = Instant.parse("2024-10-01T12:00:00Z"),
                        repeatMode = RepeatMode.DAILY,
                        completed = false
                    )
                ),
                Note(
                    id = 12L,
                    title = "Note 12",
                    content = "Content of note 12",
                    type = NoteType.SIMPLE,
                    pinned = false,
                    reminder = Reminder(
                        time = Instant.parse("2024-10-01T12:00:00Z"),
                        repeatMode = RepeatMode.WEEKLY,
                        completed = true
                    )
                ),
                Note(
                    id = 13L,
                    title = "Note 13",
                    content = "Content of note 13",
                    type = NoteType.SIMPLE,
                    items = listOf(
                        Item(
                            id = 7L,
                            content = "Item 7",
                            checked = false
                        ),
                        Item(
                            id = 8L,
                            content = "Item 8",
                            checked = true
                        ),
                        Item(
                            id = 9L,
                            content = "Item 9",
                            checked = false
                        )
                    )
                ),
                Note(
                    id = 14L,
                    title = "Note 14",
                    content = "Content of note 14",
                    type = NoteType.SIMPLE,
                    items = listOf(
                        Item(
                            id = 10L,
                            content = "Item 10",
                            checked = false
                        ),
                        Item(
                            id = 11L,
                            content = "Item 11",
                            checked = true
                        ),
                        Item(
                            id = 12L,
                            content = "Item 12",
                            checked = false
                        )
                    )
                )
            )
        )
    )
}