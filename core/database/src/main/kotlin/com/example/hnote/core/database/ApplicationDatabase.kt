package com.example.hnote.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hnote.core.database.dao.NoteDao
import com.example.hnote.core.database.dao.NoteFtsDao
import com.example.hnote.core.database.dao.SearchQueryDao
import com.example.hnote.core.database.model.ItemEntity
import com.example.hnote.core.database.model.ItemFtsEntity
import com.example.hnote.core.database.model.NoteEntity
import com.example.hnote.core.database.model.NoteFtsEntity
import com.example.hnote.core.database.model.ReminderEntity
import com.example.hnote.core.database.model.SearchQueryEntity
import com.example.hnote.core.database.util.InstantConverter
import com.example.hnote.core.database.util.RepeatModeConverter

@Database(
    entities = [
        NoteEntity::class,
        ReminderEntity::class,
        ItemEntity::class,
        SearchQueryEntity::class,
        NoteFtsEntity::class,
        ItemFtsEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(
        InstantConverter::class,
    RepeatModeConverter::class
)
internal abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun searchQueryDao(): SearchQueryDao
    abstract fun noteFtsDao(): NoteFtsDao
}