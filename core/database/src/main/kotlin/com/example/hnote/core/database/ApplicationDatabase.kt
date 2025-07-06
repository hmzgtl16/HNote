package com.example.hnote.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hnote.core.database.dao.ItemFtsDao
import com.example.hnote.core.database.dao.NoteDao
import com.example.hnote.core.database.dao.NoteFtsDao
import com.example.hnote.core.database.dao.SearchQueryDao
import com.example.hnote.core.database.model.ItemEntity
import com.example.hnote.core.database.model.NoteEntity
import com.example.hnote.core.database.model.SearchQueryEntity
import com.example.hnote.core.database.util.InstantConverter
import com.example.hnote.core.database.util.NoteTypeConverter
import com.example.hnote.core.database.util.ReminderRepeatModeConverter

@Database(
    entities = [
        NoteEntity::class,
        ItemEntity::class,
        SearchQueryEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(
        InstantConverter::class,
        NoteTypeConverter::class,
        ReminderRepeatModeConverter::class
)
internal abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun searchQueryDao(): SearchQueryDao
    abstract fun noteFtsDao(): NoteFtsDao
    abstract fun itemFtsDao(): ItemFtsDao

}