package com.example.hnote.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.hnote.core.database.model.ItemEntity
import com.example.hnote.core.database.model.NoteEntity
import com.example.hnote.core.database.model.NoteWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert
    suspend fun upsertNote(note: NoteEntity): Long

    @Upsert
    suspend fun upsertItems(items: List<ItemEntity>)

    @Transaction
    suspend fun upsertNoteWithItems(note: NoteEntity, items: List<ItemEntity>) {
        val noteId = upsertNote(note).takeUnless { it == -1L } ?: note.id
        items
            .map { it.copy(noteId = noteId) }
            .also { upsertItems(items = it) }
    }

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Delete
    suspend fun deleteNotes(notes: List<NoteEntity>)

    @Delete
    suspend fun deleteItem(item: ItemEntity)

    @Delete
    suspend fun deleteItems(items: List<ItemEntity>)

    @Transaction
    @Query("SELECT * FROM notes ORDER BY pinned DESC, updatedAt DESC")
    fun getAllNotes(): Flow<List<NoteWithItems>>

    @Transaction
    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Long): Flow<NoteWithItems?>

    @Transaction
    @Query("SELECT * FROM notes WHERE id IN (:ids)")
    fun getNotesByIds(ids: Set<Long>): Flow<List<NoteWithItems>>

    @Transaction
    @Query("SELECT * FROM items WHERE id IN (:ids)")
    fun getItemsByIds(ids: Set<Long>): Flow<List<NoteWithItems>>
}