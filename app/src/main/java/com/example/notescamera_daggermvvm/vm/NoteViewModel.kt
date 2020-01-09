package com.example.notescamera_daggermvvm.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notescamera_daggermvvm.db.Note
import javax.inject.Inject


//NoteViewModel << NoteRepository << noteDao << NoteDataBase <<  context_application
class NoteViewModel @Inject constructor(repoRepository: NoteRepository): ViewModel(){

    private val repository: NoteRepository = repoRepository

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
        return repository.getAllNotes()
    }
}