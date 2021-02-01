package com.example.example1811.model

import com.example.example1811.Repository.Repository
import com.example.example1811.db.Note


import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NoteViewModel(private val repository: Repository) : ViewModel(), Observable {

    val note = repository.allnote
    private var isUpdateOrDelete = false
    private lateinit var noteOnUpdate: Note
    private lateinit var firebaseDatabase: FirebaseDatabase
    private var noteRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    init {
        firebaseDatabase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()
        noteRef = firebaseDatabase.getReference(mAuth.currentUser?.uid.toString())
    }



    fun insert(note: Note) = GlobalScope.launch {

        if (repository.checkIfRowExists(note)) {

        } else {
            val newRowId = repository.insert(note)
            if (newRowId > -1) {

            } else {

            }
        }
    }

    fun update(note: Note) = GlobalScope.launch {
        val noOfRows = repository.update(note)
        if (noOfRows > 0) {
            isUpdateOrDelete = false

        } else {

        }

    }

    fun delete(note: Note) = GlobalScope.launch {
        val noOfRowsDeleted = repository.delete(note)
        if (noOfRowsDeleted > 0) {
            isUpdateOrDelete = false
             } else {

        }

    }

    fun clearAll() = GlobalScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if (noOfRowsDeleted > 0) {

        } else {

        }
    }

    fun initUpdateAndDelete(note: Note) {
        isUpdateOrDelete = true
        noteOnUpdate = note


    }


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}