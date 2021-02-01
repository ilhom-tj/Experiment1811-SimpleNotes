package com.example.example1811.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao  {

    @Insert suspend fun insert(note: Note):Long

    @Update fun update(note: Note):Int

    @Delete fun delete(note: Note):Int

    @Query("DELETE FROM notes_table")
    suspend fun deleteAll():Int

    @Query("SELECT * FROM notes_table")
    fun getAllNote():LiveData<List<Note>>

    @Query("SELECT EXISTS(SELECT * FROM notes_table WHERE note_gid = :id)")
    fun isRowIsExist(id : String) : Boolean

}