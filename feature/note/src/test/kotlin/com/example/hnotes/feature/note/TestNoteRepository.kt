package com.example.hnotes.feature.note

import com.example.hnotes.core.data.repository.NoteRepository
import com.example.hnotes.core.model.Item
import com.example.hnotes.core.model.Note
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class TestNoteRepository() : NoteRepository {

    override val notes: MutableSharedFlow<List<Note>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override suspend fun getNoteById(id: Long): Flow<Note?> =
        notes.filter { it.any { note -> note.id == id } }
            .map { it.first { note -> note.id == id } }

    override suspend fun upsertNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun updateNotes(notes: List<Note>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNotes(notes: List<Note>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItem(item: Item) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItems(items: List<Item>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNoteReminder(noteId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItemsExcludingIds(
        noteId: Long,
        ids: List<Long>
    ) {
        TODO("Not yet implemented")
    }

}
