package com.example.hnote.core.data.repository

import com.example.hnote.core.alarm.AlarmScheduler
import com.example.hnote.core.data.util.toEntity
import com.example.hnote.core.data.util.toModel
import com.example.hnote.core.database.dao.NoteDao
import com.example.hnote.core.database.model.NoteWithItemsAndReminder
import com.example.hnote.core.model.Item
import com.example.hnote.core.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val alarmScheduler: AlarmScheduler
) : NoteRepository {

    override val notes: Flow<List<Note>> =
        noteDao.getAllNotes()
            .map { it.map(NoteWithItemsAndReminder::toModel) }

    override suspend fun getNoteById(id: Long): Flow<Note?> =
        noteDao.getNoteById(id = id)
            .map { it?.toModel() }

    override suspend fun addNote(note: Note) {
        noteDao.upsertNoteWithItems(
            note = note.toEntity(),
            reminder = note.reminder?.toEntity(),
            items = note.items.map(Item::toEntity)
        )
    }

    override suspend fun updateNote(note: Note) {
        noteDao.upsertNoteWithItems(
            note = note.toEntity(),
            reminder = note.reminder?.toEntity(),
            items = note.items.map(Item::toEntity)
        )

        note.reminder?.let {
            deleteNoteReminder(noteId = note.id)
            alarmScheduler.cancel(id = it.id)
            alarmScheduler.schedule(
                id = it.id,
                scheduleTime = it.time,
                repeatMode = it.repeatMode
            )
        }

        deleteItemsExcludingIds(
            noteId = note.id,
            ids = note.items.map(Item::id)
        )
    }

    override suspend fun updateNotes(notes: List<Note>) {
        notes.forEach {
            noteDao.upsertNoteWithItems(
                note = it.toEntity(),
                reminder = it.reminder?.toEntity(),
                items = it.items.map(Item::toEntity)
            )
        }
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note = note.toEntity())
    }

    override suspend fun deleteNotes(notes: List<Note>) {
        noteDao.deleteNotes(notes = notes.map(Note::toEntity))
    }

    override suspend fun deleteItem(item: Item) {
        noteDao.deleteItem(item = item.toEntity())
    }

    override suspend fun deleteItems(items: List<Item>) {
        noteDao.deleteItems(items = items.map(Item::toEntity))
    }

    override suspend fun deleteNoteReminder(noteId: Long) {
        noteDao.deleteReminderByNoteId(noteId = noteId)
    }

    override suspend fun deleteItemsExcludingIds(noteId: Long, ids: List<Long>) {
        noteDao.deleteItemsExcludingIds(noteId = noteId, ids = ids.toSet())
    }
}