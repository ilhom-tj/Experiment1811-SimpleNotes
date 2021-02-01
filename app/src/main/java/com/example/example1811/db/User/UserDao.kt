package com.example.example1811.db.User

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User) : Long

    @Query("DELETE  FROM user_table")
    fun delete() : Int

    @Query("SELECT id FROM user_table")
    fun getId() : LiveData<Int>

    @Query("SELECT isAuthed FROM user_table")
    fun getIsAuthed() : LiveData<Boolean>
}