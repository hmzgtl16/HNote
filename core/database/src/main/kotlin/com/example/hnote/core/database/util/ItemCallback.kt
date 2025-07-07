package com.example.hnote.core.database.util

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

val ItemCallback = object : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.apply {
            execSQL(sql = itemInsertTrigger)
            db.execSQL(sql = itemUpdateTrigger)
            db.execSQL(sql = itemDeleteTrigger)
        }
    }
}

private const val itemInsertTrigger =
    """
        CREATE TRIGGER IF NOT EXISTS insert_item_fts AFTER INSERT ON items
        BEGIN 
            INSERT INTO items_fts(item_fts_id, item_fts_content, item_fts_noteId)
            VALUES (new.id, new.content, new.noteId);
        END;
    """

private const val itemUpdateTrigger =
    """
        CREATE TRIGGER IF NOT EXISTS update_item_fts AFTER UPDATE ON items
        BEGIN 
            UPDATE items_fts
            SET item_fts_content = new.content
            WHERE item_fts_id = new.id;
        END;
    """

private const val itemDeleteTrigger =
    """
        CREATE TRIGGER IF NOT EXISTS delete_item_fts AFTER DELETE ON items
        BEGIN 
            DELETE FROM items_fts
            WHERE item_fts_id = old.id;
        END;
    """