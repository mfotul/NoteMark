package com.example.notemark.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notemark.core.database.notemark.NoteMarkDao
import com.example.notemark.core.database.notemark.NoteMarkEntity

@Database(
    entities = [NoteMarkEntity::class],
    version = 1
)
abstract class NoteMarkDatabase: RoomDatabase() {
    abstract val noteMarkDao: NoteMarkDao
}