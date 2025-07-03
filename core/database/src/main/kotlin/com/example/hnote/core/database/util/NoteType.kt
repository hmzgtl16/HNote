package com.example.hnote.core.database.util

enum class NoteType {
    Simple,
    Reminder,
    Checklist
}

fun Int.asNoteType(): NoteType =
    NoteType.entries.first { it.ordinal == this }