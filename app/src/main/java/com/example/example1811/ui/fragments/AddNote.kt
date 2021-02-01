package com.example.example1811.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.example1811.R
import com.example.example1811.Repository.Repository
import com.example.example1811.databinding.FragmentAddNoteBinding
import com.example.example1811.db.Note
import com.example.example1811.db.NoteDataBase
import com.example.example1811.model.NoteViewModel
import com.example.example1811.model.NoteViewModelFactory
import com.example.example1811.ui.CustomFragment
import java.util.*


class AddNote : CustomFragment() {


    override fun onBackPressed(): Boolean {
        Log.e("EROOR", "DS")
        return true
    }

    private lateinit var noteVM: NoteViewModel

    private lateinit var binding: FragmentAddNoteBinding

    private lateinit var navController: NavController

    var shouldntClose = true

    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this.requireContext()
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                saveNoteBeforeExit()
            }
        })

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val dao = NoteDataBase.getInstance(activity?.application!!).noteDao
        val repository = Repository(dao)
        val factory = NoteViewModelFactory(repository)

        noteVM = ViewModelProvider(this, factory).get(NoteViewModel::class.java)

        binding.saveNote.setOnClickListener {
            val note: Note
            note = Note(0,
                binding.title.text.toString(),
                binding.content.text.toString(),
                UUID.randomUUID().toString())
            noteVM.insert(note)
            Navigation.findNavController(view).navigate(R.id.action_addNote_to_listFragment)
        }
        binding.backButton.setOnClickListener{
            saveNoteBeforeExit()
        }


    }

    fun saveNoteBeforeExit(){
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle("Сохранить заметку ?")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            noteVM.insert(
                Note(
                    0,
                    binding.title.text.toString(),
                    binding.content.text.toString(),
                    UUID.randomUUID().toString()
                )
            )

            navController.navigate(R.id.action_addNote_to_listFragment)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            navController.navigate(R.id.action_addNote_to_listFragment)
        }

        builder.show()
    }


}
