package com.example.hnote.core.data.util

import com.example.hnote.core.database.model.ItemEntity
import com.example.hnote.core.database.model.NoteEntity
import com.example.hnote.core.database.model.NoteWithItemsAndReminder
import com.example.hnote.core.database.model.ReminderEntity
import com.example.hnote.core.database.model.SearchQueryEntity
import com.example.hnote.core.model.Item
import com.example.hnote.core.model.Note
import com.example.hnote.core.model.NoteType
import com.example.hnote.core.model.Reminder
import com.example.hnote.core.model.RepeatMode
import com.example.hnote.core.model.SearchQuery
import com.example.hnote.core.database.util.NoteType as NoteTypeEntity
import com.example.hnote.core.database.util.ReminderRepeatMode as ReminderRepeatModeEntity

fun Note.toEntity(): NoteEntity = NoteEntity(
    id = id,
    title = title,
    content = content,
    pinned = pinned,
    backgroundColor = backgroundColor,
    createdAt = created,
    updatedAt = updated,
    type = type.toEntity()
)

fun Reminder.toEntity(noteId: Long = 0L): ReminderEntity = ReminderEntity(
    id = id,
    time = time,
    repeatMode = repeatMode.toEntity(),
    completed = false,
    noteId = noteId
)

fun Item.toEntity(noteId: Long = 0L): ItemEntity = ItemEntity(
    id = id,
    content = content,
    checked = checked,
    noteId = noteId
)

fun ReminderEntity.toModel(): Reminder = Reminder(
    time = time,
    repeatMode = repeatMode.toModel()
)

fun ItemEntity.toModel(): Item = Item(
    id = id,
    content = content,
    checked = checked
)

fun NoteWithItemsAndReminder.toModel(): Note = Note(
    id = note.id,
    title = note.title,
    content = note.content,
    pinned = note.pinned,
    backgroundColor = note.backgroundColor,
    created = note.createdAt,
    updated = note.updatedAt,
    type = note.type.toModel(),
    reminder = reminder?.toModel(),
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

fun RepeatMode.toEntity(): ReminderRepeatModeEntity = when (this) {
    RepeatMode.NONE -> ReminderRepeatModeEntity.NONE
    RepeatMode.DAILY -> ReminderRepeatModeEntity.DAILY
    RepeatMode.WEEKLY -> ReminderRepeatModeEntity.WEEKLY
    RepeatMode.MONTHLY -> ReminderRepeatModeEntity.MONTHLY
    RepeatMode.YEARLY -> ReminderRepeatModeEntity.YEARLY
}

fun ReminderRepeatModeEntity.toModel(): RepeatMode = when (this) {
    ReminderRepeatModeEntity.NONE -> RepeatMode.NONE
    ReminderRepeatModeEntity.DAILY -> RepeatMode.DAILY
    ReminderRepeatModeEntity.WEEKLY -> RepeatMode.WEEKLY
    ReminderRepeatModeEntity.MONTHLY -> RepeatMode.MONTHLY
    ReminderRepeatModeEntity.YEARLY -> RepeatMode.YEARLY
}

fun SearchQuery.toEntity(): SearchQueryEntity = SearchQueryEntity(
    text = query,
    queried = queried
)

fun SearchQueryEntity.toModel(): SearchQuery = SearchQuery(
    query = text,
    queried = queried
)
