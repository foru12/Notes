package com.example.testentries2.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testentries2.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}