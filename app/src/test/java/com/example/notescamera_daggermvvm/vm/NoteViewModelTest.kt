package com.example.notescamera_daggermvvm.vm

import androidx.lifecycle.LiveData
import com.example.notescamera_daggermvvm.db.Note
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class NoteViewModelTest {

    private val testNote = TestUtilUnit.createNote("title","description",0)

    @MockK
    lateinit var noteLiveData : LiveData<List<Note>>

    @MockK
    lateinit var noteRepository: NoteRepository
    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @InjectMockKs
    lateinit var noteViewModel: NoteViewModel

    @Before
    fun setUp() {
        noteViewModel = NoteViewModel(noteRepository)
    }

    @Test
    fun insertTest(){
        noteViewModel.insert(testNote)
        verify { noteRepository.insert(testNote) }
    }

    @Test
    fun updateTest(){
        noteViewModel.update(testNote)
        verify { noteRepository.update(testNote) }
    }

    @Test
    fun deleteTest(){
        noteViewModel.delete(testNote)
        verify { noteRepository.delete(testNote) }
    }

    @Test
    fun deleteAllNotesTest(){
        noteViewModel.deleteAll()
        verify { noteRepository.deleteAllNotes() }
    }

    @Test
    fun getAllNotesTest(){
        every { noteRepository.getAllNotes() } returns noteLiveData
        noteViewModel.getAllNotes()
        verify { noteRepository.getAllNotes() }
    }

}