package com.example.notescamera_daggermvvm.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notescamera_daggermvvm.db.Note
import javax.inject.Inject


//NoteViewModel << NoteRepository << noteDao << NoteDataBase <<  context_application
class NoteViewModel @Inject constructor(repoRepository: NoteRepository): ViewModel(){

    private val repository: NoteRepository //= NoteRepository(application)
    private var allNotes :LiveData<List<Note>> //= repository.getAllNotes()

    init {
        Log.v("init NoteViewModel","hello")
//        val noteDao = NoteDatabase.getInstance(application).noteDao()
//        repository = NoteRepository(noteDao)
        repository = repoRepository
        allNotes = repository.getAllNotes()
    }

    fun insert(note: Note){
        repository.insert(note)
    }
    fun update(note: Note){
        repository.update(note)
    }
    fun delete(note: Note){
        repository.delete(note)
    }
    fun deleteAll(){
        repository.deleteAllNotes()
    }
    fun getAllNotes():LiveData<List<Note>> {
        return allNotes
    }
}