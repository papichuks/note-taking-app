package com.example.notetakingapp

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notetakingapp.adapter.NoteAdapter
import com.example.notetakingapp.databinding.FragmentHomeBinding
import com.example.notetakingapp.databinding.FragmentUpdateNoteBinding
import com.example.notetakingapp.model.Note
import com.example.notetakingapp.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*


class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NoteViewModel

    private lateinit var currentNote: Note

    var label: String = "1"

    private val args: UpdateNoteFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateNoteBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!

        binding.etNoteTitleUpdate.setText(currentNote.noteTitle)
        binding.etNoteBodyUpdate.setText(currentNote.noteBody)

        binding.tvPersonal.setOnClickListener {
            label = "1"
            binding.tvPersonal.setBackgroundColor(Color.BLUE)
            binding.tvSchool.setBackgroundColor(Color.WHITE)
            binding.tvWork.setBackgroundColor(Color.WHITE)
        }
        binding.tvSchool.setOnClickListener {
            label = "2"
            binding.tvSchool.setBackgroundColor(Color.RED)
            binding.tvPersonal.setBackgroundColor(Color.WHITE)
            binding.tvWork.setBackgroundColor(Color.WHITE)
        }
        binding.tvWork.setOnClickListener {
            label = "3"
            binding.tvWork.setBackgroundColor(Color.YELLOW)
            binding.tvPersonal.setBackgroundColor(Color.WHITE)
            binding.tvSchool.setBackgroundColor(Color.WHITE)
        }

        when(currentNote.label){
            "1" -> {
                label = "1"
                binding.tvPersonal.setBackgroundColor(Color.BLUE)
                binding.tvSchool.setBackgroundColor(Color.WHITE)
                binding.tvWork.setBackgroundColor(Color.WHITE)
            }
            "2" -> {
                label = "2"
                binding.tvSchool.setBackgroundColor(Color.RED)
                binding.tvPersonal.setBackgroundColor(Color.WHITE)
                binding.tvWork.setBackgroundColor(Color.WHITE)
            }
            "3" -> {
                label = "3"
                binding.tvWork.setBackgroundColor(Color.YELLOW)
                binding.tvPersonal.setBackgroundColor(Color.WHITE)
                binding.tvSchool.setBackgroundColor(Color.WHITE)
            }
        }



        binding.fabDone.setOnClickListener {
            val title = binding.etNoteTitleUpdate.text.toString().trim()
            val body = binding.etNoteBodyUpdate.text.toString().trim()

            val d = Date()
            val date: CharSequence = android.text.format.DateFormat
                .format("MMM d, yyyy", d.time)


            if (title.isNotEmpty()){
                val note =
                    Note(currentNote.id, title, body, timeStamp = date.toString(), label = label)
                println(label)
                notesViewModel.updateNote(note)
                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }
            else {
                Toast.makeText(
                    context,
                    "Please enter note title",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    private fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure you want to delete this note?")
            setPositiveButton("Delete"){_, _ ->
                notesViewModel.deleteNote(currentNote)

                view?.findNavController()?.navigate(
                    R.id.action_updateNoteFragment_to_homeFragment
                )
            }
            setNegativeButton("Cancel", null)
        }.create().show()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_update_note, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete -> {
                deleteNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}