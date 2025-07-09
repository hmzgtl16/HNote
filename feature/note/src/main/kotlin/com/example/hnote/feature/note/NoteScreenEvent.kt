package com.example.hnote.feature.note

import com.example.hnote.core.model.Reminder

interface NoteScreenEvent {
    data class TitleChanged(val title: String) : NoteScreenEvent
    data class ContentChanged(val content: String) : NoteScreenEvent
    data class BackgroundColorChanged(val color: Int?) : NoteScreenEvent
    data class ReminderChanged(val reminder: Reminder?) : NoteScreenEvent
    data class ReminderPickerVisibilityChanged(val isVisible: Boolean) : NoteScreenEvent
    data class PaletteVisibilityChanged(val isVisible: Boolean) : NoteScreenEvent
    data class DeleteDialogVisibilityChanged(val isVisible: Boolean) : NoteScreenEvent
    data object Undo : NoteScreenEvent
    data object Redo : NoteScreenEvent
    data object SaveNote : NoteScreenEvent
    data object CopyNote : NoteScreenEvent
    data object DeleteNote : NoteScreenEvent
}

