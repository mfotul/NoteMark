package com.example.notemark.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notemark.core.data.database.notemark.NoteMarkDao
import com.example.notemark.core.data.database.notemark.NoteMarkEntity

@Database(
    entities = [NoteMarkEntity::class],
    version = 1
)
abstract class NoteMarkDatabase: RoomDatabase() {
    abstract val noteMarkDao: NoteMarkDao
}