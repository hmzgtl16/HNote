package com.example.hnote.core.database.util

enum class NoteType {
    SIMPLE,
    REMINDER,
    CHECK_LIST
}

fun Int.asNoteType(): NoteType =
    NoteType.entries.first { it.ordinal == this }