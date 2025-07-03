package com.example.notemark.core.data.database.notemark

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteMarkDao {
    @Query("SELECT * FROM notemarkentity ORDER BY lastEditedAt DESC")
    fun getAllNotes(): Flow<List<NoteMarkEntity>>

    @Query("SELECT * FROM notemarkentity WHERE id = :id")
    fun getNoteById(id: String): Flow<NoteMarkEntity>

    @Delete
    suspend fun delete(noteMarkEntity: NoteMarkEntity): Int

    @Upsert
    suspend fun upsert(noteMarkEntity: NoteMarkEntity)

    @Query("DELETE FROM notemarkentity")
    suspend fun deleteAllNotes()
}