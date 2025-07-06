package com.example.hnote.core.data.util

import com.example.hnote.core.database.model.ItemEntity
import com.example.hnote.core.database.model.NoteEntity
import com.example.hnote.core.database.model.NoteWithItems
import com.example.hnote.core.model.Item
import com.example.hnote.core.model.Note
import com.example.hnote.core.model.NoteType
import com.example.hnote.core.model.Reminder
import com.example.hnote.core.model.ReminderRepeatMode
import kotlinx.datetime.Instant
import com.example.hnote.core.database.util.NoteType as NoteTypeEntity
import com.example.hnote.core.database.util.ReminderRepeatMode as ReminderRepeatModeEntity

fun Note.toEntity(): NoteEntity = NoteEntity(
    id = this.id,
    title = this.title,
    content = this.content,
    pinned = this.pinned,
    backgroundColor = backgroundColor,
    createdAt = created,
    updatedAt = updated,
    type = type.toEntity(),
    reminder = reminder?.time,
    reminderMode = reminder?.repeatMode?.toEntity(),
    completed = reminder?.completed
)

fun Note.toItemEntities(): List<ItemEntity> = items
    .map { it.toEntity(noteId = id) }

fun Item.toEntity(noteId: Long = 0L): ItemEntity = ItemEntity(
    id = this.id,
    content = this.content,
    checked = this.checked,
    noteId = noteId
)

fun ItemEntity.toModel(): Item = Item(
    id = this.id,
    content = this.content,
    checked = this.checked
)

fun NoteWithItems.toModel(): Note = Note(
    id = note.id,
    title = note.title,
    content = note.content,
    pinned = note.pinned,
    backgroundColor = note.backgroundColor,
    created = note.createdAt,
    updated = note.updatedAt,
    type = note.type.toModel(),
    reminder = Reminder(
        time = note.reminder ?: Instant.DISTANT_FUTURE,
        repeatMode = note.reminderMode?.toModel() ?: ReminderRepeatMode.NONE,
        completed = note.completed ?: false
    ),
    items = items.map(ItemEntity::toModel)
)

fun NoteType.toEntity(): NoteTypeEntity = when (this) {
    NoteType.SIMPLE -> NoteTypeEntity.SIMPLE
    NoteType.REMINDER -> NoteTypeEntity.REMINDER
    NoteType.CHECK_LIST -> NoteTypeEntity.CHECK_LIST
}

fun NoteTypeEntity.toModel(): NoteType = when (this) {
    NoteTypeEntity.SIMPLE -> NoteType.SIMPLE
    NoteTypeEntity.REMINDER -> NoteType.REMINDER
    NoteTypeEntity.CHECK_LIST -> NoteType.CHECK_LIST
}

fun ReminderRepeatMode.toEntity(): ReminderRepeatModeEntity = when (this) {
    ReminderRepeatMode.NONE -> ReminderRepeatModeEntity.NONE
    ReminderRepeatMode.DAILY -> ReminderRepeatModeEntity.DAILY
    ReminderRepeatMode.WEEKLY -> ReminderRepeatModeEntity.WEEKLY
    ReminderRepeatMode.MONTHLY -> ReminderRepeatModeEntity.MONTHLY
    ReminderRepeatMode.YEARLY -> ReminderRepeatModeEntity.YEARLY
}

fun ReminderRepeatModeEntity.toModel(): ReminderRepeatMode = when (this) {
    ReminderRepeatModeEntity.NONE -> ReminderRepeatMode.NONE
    ReminderRepeatModeEntity.DAILY -> ReminderRepeatMode.DAILY
    ReminderRepeatModeEntity.WEEKLY -> ReminderRepeatMode.WEEKLY
    ReminderRepeatModeEntity.MONTHLY -> ReminderRepeatMode.MONTHLY
    ReminderRepeatModeEntity.YEARLY -> ReminderRepeatMode.YEARLY
}