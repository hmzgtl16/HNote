package com.example.hnote.core.data

import com.example.hnote.core.database.dao.NoteDao
import com.example.hnote.core.database.model.ItemEntity
import com.example.hnote.core.database.model.NoteEntity
import com.example.hnote.core.database.model.NoteWithItemsAndReminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class NoteDaoTest : NoteDao {

    private val noteEntitiesStateFlow =
        MutableStateFlow(value = emptyList<NoteEntity>())

    private val itemEntitiesStateFlow =
        MutableStateFlow(value = emptyList<ItemEntity>())

    override suspend fun upsertNote(note: NoteEntity): Long {
        noteEntitiesStateFlow.update { it ->
            (it.filter { entity -> entity.id != note.id } + note)
                .distinctBy { entity -> entity.id }
        }
        return note.id.takeIf { it != 0L } ?: noteEntitiesStateFlow.value.size.toLong()
    }

    override suspend fun upsertItems(items: List<ItemEntity>) =
        itemEntitiesStateFlow.update { it ->
            (it.filter { entity -> entity.id !in items.map(ItemEntity::id) } + items)
                .distinctBy { entity -> entity.id }
        }

    override suspend fun upsertNoteWithItems(note: NoteEntity, items: List<ItemEntity>) {
        val noteId = upsertNote(note).takeIf { it != -1L } ?: note.id
        val updatedItems = items.map { it.copy(noteId = noteId) }
        upsertItems(updatedItems)
    }

    override suspend fun deleteNote(note: NoteEntity) =
        noteEntitiesStateFlow.update { it ->
            it.filterNot { entity -> entity.id == note.id }
        }


    override suspend fun deleteNotes(notes: List<NoteEntity>) =
        noteEntitiesStateFlow.update { it ->
            it.filterNot { entity -> entity.id in notes.map(NoteEntity::id) }
        }

    override suspend fun deleteItem(item: ItemEntity) =
        itemEntitiesStateFlow.update { it ->
            it.filterNot { entity -> entity.id == item.id }
        }

    override suspend fun deleteItems(items: List<ItemEntity>) =
        itemEntitiesStateFlow.update {
            it.filterNot { entity ->
                entity.id in items.map(ItemEntity::id)
            }
        }

    override fun getAllNotes(): Flow<List<NoteWithItemsAndReminder>> =
        noteEntitiesStateFlow
            .combine(itemEntitiesStateFlow) { notes, items ->
                notes.map { note ->
                    NoteWithItemsAndReminder(
                        note = note,
                        items = items.filter { it.noteId == note.id }
                    )
                }
            }

    override fun getNoteById(id: Long): Flow<NoteWithItemsAndReminder?> =
        noteEntitiesStateFlow
            .combine(itemEntitiesStateFlow) { notes, items ->
                notes.find { it.id == id }?.let { note ->
                    NoteWithItemsAndReminder(
                        note = note,
                        items = items.filter { it.noteId == note.id }
                    )
                }
            }
}