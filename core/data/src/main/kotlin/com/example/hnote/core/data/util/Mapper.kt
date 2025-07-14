package com.example.hnote.core.data.util

import com.example.hnote.core.database.model.ItemEntity
import com.example.hnote.core.database.model.NoteEntity
import com.example.hnote.core.database.model.NoteWithItemsAndReminder
import com.example.hnote.core.database.model.ReminderEntity
import com.example.hnote.core.database.model.SearchQueryEntity
import com.example.hnote.core.model.Item
import com.example.hnote.core.model.Note
import com.example.hnote.core.model.Reminder
import com.example.hnote.core.model.RepeatMode
import com.example.hnote.core.model.SearchQuery
import com.example.hnote.core.database.util.RepeatMode as ReminderRepeatModeEntity

fun Note.toEntity(): NoteEntity = NoteEntity(
    id = id,
    title = title,
    content = content,
    pinned = pinned,
    backgroundColor = backgroundColor,
    createdAt = created,
    updatedAt = updated
)

fun Reminder.toEntity(noteId: Long = 0L): ReminderEntity = ReminderEntity(
    id = id,
    time = time,
    repeatMode = repeatMode.toEntity(),
    noteId = noteId
)

fun Item.toEntity(noteId: Long = 0L): ItemEntity = ItemEntity(
    id = id,
    content = content,
    checked = checked,
    noteId = noteId
)

fun ReminderEntity.toModel(): Reminder = Reminder(
    id = id,
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
    reminder = reminder?.toModel(),
    items = items.map(ItemEntity::toModel)
)

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
