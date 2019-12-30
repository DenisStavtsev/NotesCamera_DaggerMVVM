package com.example.notescamera_daggermvvm.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM Note_Table")
    fun deleteAllnotes()

    @Query("SELECT * FROM Note_Table ORDER BY Priority DESC")
    fun getAllNotes():LiveData<List<Note>>
}