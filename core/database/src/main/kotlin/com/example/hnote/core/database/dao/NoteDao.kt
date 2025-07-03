package com.example.hnote.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.hnote.core.database.model.ItemEntity
import com.example.hnote.core.database.model.NoteEntity
import com.example.hnote.core.database.model.NoteWithItems
import com.example.hnote.core.database.util.NoteType
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("SELECT * FROM notes ORDER BY pinned DESC, updatedAt DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: Long): NoteEntity?

    @Transaction
    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteWithItems(noteId: Long): NoteWithItems?

    @Transaction
    @Query("SELECT * FROM notes WHERE type = :type ORDER BY updatedAt DESC")
    fun getAllChecklistNotes(type: NoteType = NoteType.CHECK_LIST): Flow<List<NoteWithItems>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: ItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItems(items: List<ItemEntity>)

    @Update
    suspend fun updateItem(item: ItemEntity)

    @Delete
    suspend fun deleteItem(item: ItemEntity)

    @Query("SELECT * FROM items WHERE noteId = :noteId")
    suspend fun getItemsForNote(noteId: Long): List<ItemEntity>
}