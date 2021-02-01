package com.example.example1811.db.User

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id  : Int,
    var isAuthed : Boolean = false,
)