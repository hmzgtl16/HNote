package com.example.hnote.feature.note

import com.example.hnote.core.model.Item
import com.example.hnote.core.model.Note
import com.example.hnote.core.model.Reminder

data class NoteUiState(
    val note: Note? = Note(),
    val title: String = "",
    val content: String = "",
    val backgroundColor: Int? = null,
    val reminder: Reminder? = null,
    val items: List<Item> = emptyList(),
    val isEdited: Boolean = false,
    val isReminderPickerVisible: Boolean = false,
    val isPaletteVisible: Boolean = false,
    val isDeleteDialogVisible: Boolean = false,
    val isDeleted: Boolean = false,
    val canUndo: Boolean = false,
    val canRedo: Boolean = false,
) {

    fun getEditableSnapshot(): EditableNoteState = EditableNoteState(
        title = title,
        content = content,
        backgroundColor = backgroundColor,
        reminder = reminder,
        items = items
    )
}

data class EditableNoteState(
    val title: String,
    val content: String,
    val backgroundColor: Int?,
    val reminder: Reminder?,
    val items: List<Item>
)