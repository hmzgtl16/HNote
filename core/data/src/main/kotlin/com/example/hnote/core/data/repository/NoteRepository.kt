package com.example.hnote.core.data.repository

import com.example.hnote.core.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun addNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun updateNotes(notes: List<Note>)
    suspend fun deleteNote(noteId: String)
    suspend fun deleteNotes(noteIds: List<String>)
    suspend fun getNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: String): Flow<Note?>
}