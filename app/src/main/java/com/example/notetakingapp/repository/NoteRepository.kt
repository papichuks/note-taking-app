package com.example.notetakingapp.repository


import com.example.notetakingapp.database.NoteDatabase
import com.example.notetakingapp.model.Note

class NoteRepository(private val db: NoteDatabase) {
    suspend fun insertNote(note: Note) = db.getNoteDao().insertNote(note)
    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)
    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)
//    suspend fun insertImage(note: Note) = db.getNoteDao().insertImage(note)

    fun getAllNotes() = db.getNoteDao().getAllNotes()

    fun getPersonalNotes() = db.getNoteDao().getPersonalNotes()

    fun getSchoolNotes() = db.getNoteDao().getSchoolNotes()

    fun getWorkNotes() = db.getNoteDao().getWorkNotes()

    fun searchNote(query: String?) = db.getNoteDao().searchNote(query)
    fun getAllNotesByUserId(uId: String) = db.getNoteDao().getAllNotesByUserId(uId)
}