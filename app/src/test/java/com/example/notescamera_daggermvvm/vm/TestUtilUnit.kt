package com.example.notescamera_daggermvvm.vm

import com.example.notescamera_daggermvvm.db.Note

object TestUtilUnit {

    fun createNotes(count: Int, title: String, description: String): List<Note> {
        return (0 until count).map {
            createNote(
                title = title + it,
                description = description + it,
                priority = it%6
            )
        }
    }

    fun createNote(title: String, description: String,priority:Int) =
        Note(null,title,description,priority)
}
