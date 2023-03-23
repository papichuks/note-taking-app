package com.example.notetakingapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notetakingapp.adapter.NoteAdapter
import com.example.notetakingapp.databinding.FragmentHomeBinding
import com.example.notetakingapp.model.Note
import com.example.notetakingapp.util.safeNavigate
import com.example.notetakingapp.viewmodel.NoteViewModel
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        firebaseAuth = FirebaseAuth.getInstance()
//        val user = FirebaseAuth.getInstance().currentUser?.uid
//        val userId = user!!
        return
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).noteViewModel

        setUpRecyclerView()

        binding?.fabAddNote?.setOnClickListener{
            findNavController().safeNavigate(
                HomeFragmentDirections.actionHomeFragmentToNewNoteFragment()
            )
        }
    }

    private fun setUpRecyclerView() {
        noteAdapter = NoteAdapter()

        binding?.recyclerView.apply {
            this?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            this?.setHasFixedSize(true)
            this?.adapter = noteAdapter
        }

        activity?.let {
            notesViewModel.getAllNotes().observe(
                viewLifecycleOwner
            ) { note ->
                noteAdapter.differ.submitList(note)
                updateUI(note)
            }
        }
    }

    private fun updateUI(note: List<Note>?) {
        if (note != null) {
            if (note.isNotEmpty()){
                binding?.cardView?.visibility = View.GONE
                binding?.recyclerView?.visibility = View.VISIBLE
            }
            else {
                binding?.cardView?.visibility = View.VISIBLE
                binding?.recyclerView?.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear()
        inflater.inflate(R.menu.home_menu, menu)

        val mMenuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        mMenuSearch.isSubmitButtonEnabled = false
        mMenuSearch.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                findNavController().safeNavigate(
                    HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchNote(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchNote(newText)
        }
        return true
    }

    private fun searchNote(query: String) {
        val searchQuery = "%$query"
        notesViewModel.searchNote(searchQuery).observe(
            this,
            {list -> noteAdapter.differ.submitList(list)}
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}