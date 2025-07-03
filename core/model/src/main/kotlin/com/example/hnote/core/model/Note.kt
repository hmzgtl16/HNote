package com.example.hnote.core.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Note(
    val id: Long = 0L,
    val title: String = "",
    val content: String = "",
    val pinned: Boolean = false,
    val backgroundColor: Int? = null,
    val type: NoteType,
    val reminder: Instant? = null,
    val reminderMode: ReminderRepeatMode? = null,
    val isCompleted: Boolean? = null,
    val items: List<Item> = emptyList(),
    val created: Instant = Clock.System.now(),
    val updated: Instant = Clock.System.now()
)
