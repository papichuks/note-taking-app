package com.example.notetakingapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notetakingapp.model.Note

@Dao
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertImage(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes WHERE uId = :uId")
    fun getAllNotesByUserId(uId: String): LiveData<List<Note>>

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE label = 1")
    fun getPersonalNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE label = 2")
    fun getSchoolNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE label = 3")
    fun getWorkNotes(): LiveData<List<Note>>

//    @Query("SELECT * FROM notes WHERE label LIKE :query")
//    fun getPersonalNotes(query: String?): LiveData<List<Note>>
//
//    @Query("SELECT * FROM notes WHERE label LIKE :query")
//    fun getSchoolNotes(query: String?): LiveData<List<Note>>
//
//    @Query("SELECT * FROM notes WHERE label LIKE :query")
//    fun getWorkNotes(query: String?): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE noteTitle LIKE :query OR noteBody LIKE :query")
    fun searchNote(query: String?): LiveData<List<Note>>
}