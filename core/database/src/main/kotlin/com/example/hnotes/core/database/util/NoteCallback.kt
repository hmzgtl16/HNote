package com.example.hnotes.core.database.util

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

val NoteCallback = object : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.apply {
            execSQL(sql = noteInsertTrigger)
            db.execSQL(sql = noteUpdateTrigger)
            db.execSQL(sql = noteDeleteTrigger)
        }
    }
}

private const val noteInsertTrigger =
    """
        CREATE TRIGGER IF NOT EXISTS insert_note_fts AFTER INSERT ON notes
        BEGIN 
            INSERT INTO notes_fts(note_fts_id, note_fts_title, note_fts_content)
            VALUES (new.id, new.title, new.content);
        END;
    """

private const val noteUpdateTrigger =
    """
        CREATE TRIGGER IF NOT EXISTS update_note_fts AFTER UPDATE ON notes
        BEGIN 
            UPDATE notes_fts
            SET note_fts_title = new.title, note_fts_content = new.content
            WHERE note_fts_id = new.id;
        END;
    """

private const val noteDeleteTrigger =
    """
        CREATE TRIGGER IF NOT EXISTS delete_note_fts AFTER DELETE ON notes
        BEGIN 
            DELETE FROM notes_fts
            WHERE note_fts_id = old.id;
        END;
    """