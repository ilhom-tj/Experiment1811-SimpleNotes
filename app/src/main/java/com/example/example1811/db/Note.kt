package com.example.example1811.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    var id : Int,

    @ColumnInfo(name = "note_title")
    var title : String,

    @ColumnInfo(name = "note_content")
    var content : String,

    @ColumnInfo(name = "note_gid")
    var gid : String
)

