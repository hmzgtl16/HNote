package com.example.hnote.feature.notes

import com.example.hnote.core.model.Note

interface NotesScreenEvent {
    data class MultiSelectionChanged(val enabled: Boolean) : NotesScreenEvent
    data class NoteSelectedChanged(val note: Note) : NotesScreenEvent
    data class SelectAllNotesChecked(val checked: Boolean) : NotesScreenEvent
    data object DeleteNotes : NotesScreenEvent
    data object RestoreNotes : NotesScreenEvent
    data class PinNote(val note: Note) : NotesScreenEvent
    data class PinNotes(val isPinned: Boolean) : NotesScreenEvent
    data class NavigateToNote(val noteId: Long) : NotesScreenEvent
}