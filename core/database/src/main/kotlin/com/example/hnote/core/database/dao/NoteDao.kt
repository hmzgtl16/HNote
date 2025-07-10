package com.example.hnote.core.database.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.hnote.core.database.model.ItemEntity
import com.example.hnote.core.database.model.NoteEntity
import com.example.hnote.core.database.model.NoteWithItemsAndReminder
import com.example.hnote.core.database.model.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Upsert
    suspend fun upsertNote(note: NoteEntity): Long
    @Upsert
    suspend fun upsertReminder(reminder: ReminderEntity)
    @Upsert
    suspend fun upsertItems(items: List<ItemEntity>)
    @Transaction
    suspend fun upsertNoteWithItems(
        note: NoteEntity,
        reminder: ReminderEntity?,
        items: List<ItemEntity>
    ) {
        val noteId = upsertNote(note = note).takeUnless { it == -1L } ?: note.id
        reminder
            ?.copy(noteId = noteId)
            ?.let {
                Log.d("NoteDao", "Upserting reminder: $it")
                upsertReminder(reminder = it)
            }
        items
            .map { it.copy(noteId = noteId) }
            .also { upsertItems(items = it) }
    }
    @Delete
    suspend fun deleteNote(note: NoteEntity)
    @Delete
    suspend fun deleteNotes(notes: List<NoteEntity>)
    @Query("DELETE FROM reminders WHERE noteId = :noteId")
    suspend fun deleteReminderByNoteId(noteId: Long)
    @Delete
    suspend fun deleteItem(item: ItemEntity)
    @Delete
    suspend fun deleteItems(items: List<ItemEntity>)

    @Query("DELETE FROM items WHERE noteId = :noteId AND id NOT IN (:ids)")
    suspend fun deleteItemsExcludingIds(noteId: Long, ids: Set<Long>)
    @Transaction
    @Query("SELECT * FROM notes ORDER BY pinned DESC, updatedAt DESC")
    fun getAllNotes(): Flow<List<NoteWithItemsAndReminder>>
    @Transaction
    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Long): Flow<NoteWithItemsAndReminder?>
    @Transaction
    @Query("SELECT * FROM notes WHERE id IN (:ids)")
    fun getNotesByIds(ids: Set<Long>): Flow<List<NoteWithItemsAndReminder>>
}