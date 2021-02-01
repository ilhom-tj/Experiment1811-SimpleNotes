package com.example.example1811.Repository

import com.example.example1811.db.Note
import com.example.example1811.db.NoteDao

class Repository (private val noteDao: NoteDao) {

    val allnote = noteDao.getAllNote()

    suspend fun insert(note: Note):Long{
        return noteDao.insert(note)
    }

    suspend fun update(note: Note) : Int{
        return noteDao.update(note)
    }

    suspend fun delete(note: Note):Int{
        return noteDao.delete(note)
    }

    suspend fun deleteAll(): Int{
        return noteDao.deleteAll()
    }
    suspend fun checkIfRowExists(note: Note) : Boolean{
        return noteDao.isRowIsExist(note.gid)
    }
}