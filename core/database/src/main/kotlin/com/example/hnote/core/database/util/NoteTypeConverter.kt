package com.example.hnote.core.database.util

import androidx.room.TypeConverter

internal class NoteTypeConverter {
    @TypeConverter
    fun intToNoteType(value: Int?): NoteType? =
        value?.let(Int::asNoteType)

    @TypeConverter
    fun noteTypeToInt(noteType: NoteType?): Int? =
        noteType?.let(NoteType::ordinal)
}