package com.example.notescamera_daggermvvm.vm

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.notescamera_daggermvvm.db.Note
import com.example.notescamera_daggermvvm.db.NoteDao
import javax.inject.Inject
import javax.inject.Singleton

//NoteRepository << noteDao << NoteDataBase << context_application
@Singleton
class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    private val allNotes:LiveData<List<Note>> = noteDao.getAllNotes()

    fun insert(note: Note) {
        InsertNoteAsyncTask(
            noteDao
        ).execute(note)
    }

    fun delete(note: Note){
        DeleteNoteAsyncTask(
            noteDao
        ).execute(note)
    }

    fun update(note: Note){
        UpdateNoteAsyncTask(
            noteDao
        ).execute(note)
    }

    fun deleteAllNotes() {
        DeleteAllNotesAsyncTask(
            noteDao
        ).execute()
    }

    fun getAllNotes():LiveData<List<Note>>{
        return allNotes
    }

    companion object {

        class InsertNoteAsyncTask(private val noteDao: NoteDao): AsyncTask<Note, Void, Void>() {

            override fun doInBackground(vararg notes: Note):Void?{
                noteDao.insert(notes[0])
                return null
            }

        }

        class UpdateNoteAsyncTask(private val noteDao: NoteDao): AsyncTask<Note, Void, Void>() {

            override fun doInBackground(vararg notes: Note):Void?{
                noteDao.update(notes[0])
                return null
            }

        }

        class DeleteNoteAsyncTask(private val noteDao: NoteDao): AsyncTask<Note, Void, Void>() {

            override fun doInBackground(vararg notes: Note):Void?{
                noteDao.delete(notes[0])
                return null
            }

        }

        class DeleteAllNotesAsyncTask(private val noteDao: NoteDao): AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids:Void):Void?{
                noteDao.deleteAllnotes()
                return null
            }

        }

    }
}