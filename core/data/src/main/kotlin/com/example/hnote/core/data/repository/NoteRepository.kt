package com.example.hnote.core.data.repository

import com.example.hnote.core.model.Item
import com.example.hnote.core.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    val notes: Flow<List<Note>>
    suspend fun getNoteById(id: Long): Flow<Note?>
    suspend fun upsertNote(note: Note)
    suspend fun updateNotes(notes: List<Note>)
    suspend fun deleteNote(note: Note)
    suspend fun deleteNotes(notes: List<Note>)
    suspend fun deleteItem(item: Item)
    suspend fun deleteItems(items: List<Item>)
    suspend fun deleteNoteReminder(noteId: Long)
    suspend fun deleteItemsExcludingIds(noteId: Long, ids: List<Long>)
}