package com.example.example1811.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.example1811.R
import com.example.example1811.Repository.Repository
import com.example.example1811.databinding.FragmentListBinding
import com.example.example1811.db.Note
import com.example.example1811.db.NoteDataBase
import com.example.example1811.db.NoteFireClass
import com.example.example1811.model.NoteViewModel
import com.example.example1811.model.NoteViewModelFactory
import com.example.example1811.ui.LoginActivity
import com.example.example1811.ui.adapter.SwipeDelegate
import com.example.example1811.ui.adapter.NoteAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.lang.reflect.Type


class ListFragment : Fragment() {

    private lateinit var noteVM: NoteViewModel
    private lateinit var adapter: NoteAdapter

    private lateinit var navController: NavController

    private lateinit var  firebaseDatabase :FirebaseDatabase
    private lateinit var mAuth : FirebaseAuth
    private lateinit var noteRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseDatabase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()
        noteRef = firebaseDatabase.getReference(mAuth.currentUser!!.uid)

        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val dao = NoteDataBase.getInstance(activity?.application!!).noteDao
        val repository = Repository(dao)
        val factory = NoteViewModelFactory(repository)


        noteVM = ViewModelProvider(this,factory).get(NoteViewModel::class.java)
        binding.myViewModel = noteVM
        binding.lifecycleOwner = this


        getAllNotesFromFire()
        binding.addnotefab.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_listFragment_to_addNote)
        }
        initRecyclerView()


        Navigation.findNavController(view).currentBackStackEntry?.savedStateHandle?.getLiveData<Type>("key")?.observe(
            viewLifecycleOwner) {result ->
            Log.e("Ket",result.toString())
        }

        binding.exit.setOnClickListener{
            mAuth.signOut()
            noteVM.clearAll()
            val intent : Intent = Intent(activity,LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
    private fun initRecyclerView(){
        binding.notiesrecyler.layoutManager = LinearLayoutManager(this.context)
        adapter = NoteAdapter({ selectedItem: Note ->listItemClicked(selectedItem)})
        binding.notiesrecyler.adapter = adapter

        val swipeDelegate = object : SwipeDelegate(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)
                val position : Int = viewHolder.position
                val note : Note = allNotes[position]
                deleteOnFire(note)
                noteVM.delete(note)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeDelegate)
        itemTouchHelper.attachToRecyclerView(binding.notiesrecyler)

        displayNotesList()
    }

    private lateinit var allNotes : List<Note>
    private fun displayNotesList(){
        noteVM.note.observe(this.viewLifecycleOwner , Observer {
           adapter.setList(it)
            allNotes = it
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(subscriber: Note){
        //Toast.makeText(this,"selected name is ${subscriber.name}",Toast.LENGTH_LONG).show()
        noteVM.initUpdateAndDelete(subscriber)
    }

    override fun onStart() {
        super.onStart()
        noteVM.note.observe(this.viewLifecycleOwner , Observer {
            adapter.setList(it)
            allNotes = it
            adapter.notifyDataSetChanged()
            saveOnFire(allNotes)
        })
    }

    fun saveOnFire(notes: List<Note>){
        for (note in notes){
            noteRef.child(note.gid).setValue(note)
        }
    }

    fun deleteOnFire(note: Note){
        noteRef.child(note.gid).removeValue()
    }

    fun getAllNotesFromFire(){
        noteRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children){
                    var note = snap.getValue(NoteFireClass::class.java)
                    var note_r : Note =
                        Note(0,
                            note?.title.toString(),
                            note?.content.toString(),
                            note?.gid.toString()
                        )
                    noteVM.insert(note_r)
                    Log.e(note?.id.toString(),note?.title.toString())
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}



