package com.example.notetakingapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notetakingapp.model.Note
import com.example.notetakingapp.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(app: Application, private val noteRepository: NoteRepository): AndroidViewModel(app) {
    fun addNote(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }

    fun getAllNotes() = noteRepository.getAllNotes()

    fun getPersonalNotes() = noteRepository.getPersonalNotes()

    fun getSchoolNotes() = noteRepository.getSchoolNotes()

    fun getWorkNotes() = noteRepository.getWorkNotes()

    fun searchNote(query: String?) = noteRepository.searchNote(query)

    fun getAllNotesByUserId(uId: String) = noteRepository.getAllNotesByUserId(uId)
}