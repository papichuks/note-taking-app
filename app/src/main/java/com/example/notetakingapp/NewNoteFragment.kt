package com.example.notetakingapp

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import com.example.notetakingapp.adapter.NoteAdapter
import com.example.notetakingapp.databinding.FragmentHomeBinding
import com.example.notetakingapp.databinding.FragmentNewNoteBinding
import com.example.notetakingapp.viewmodel.NoteViewModel
import com.example.notetakingapp.model.Note
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import java.util.zip.Inflater


class NewNoteFragment : Fragment() {

    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter



//    private val user = FirebaseAuth.getInstance().currentUser?.uid
//    private val userId = user!!

    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentNewNoteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).noteViewModel
        mView = view
    }

    private fun saveNote(view: View){
        val noteTitle = binding?.etNoteTitle?.text.toString().trim()
        val noteBody = binding?.etNoteBody?.text.toString().trim()

        if (noteTitle.isNotEmpty() || noteBody.isNotEmpty()){
            val d = Date()
            val date: CharSequence = android.text.format.DateFormat
                .format("MMM d, yyyy", d.time)
            val note = Note(0, noteTitle, noteBody, timeStamp = date.toString())
            //userId should be added with initialization on top

            notesViewModel.addNote(note)

            Toast.makeText(mView.context, "Note saved successfully", Toast.LENGTH_SHORT).show()

            view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
        }
        else {
            Toast.makeText(mView.context, "Please enter note title", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        menu.clear()
        inflater.inflate(R.menu.menu_new_note, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save -> {
                saveNote(mView)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

//if (noteTitle.isNotEmpty()){
//    val note = userId?.let { Note(0, noteTitle, noteBody, it) }
//
//    if (note != null) {
//        notesViewModel.addNote(note)
//    }
//
//    Toast.makeText(mView.context, "Note saved successfully", Toast.LENGTH_SHORT).show()
//
//    view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
//}
//else {
//    Toast.makeText(mView.context, "Please enter note title", Toast.LENGTH_LONG).show()
//}