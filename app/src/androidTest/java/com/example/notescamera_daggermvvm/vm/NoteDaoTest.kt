package com.example.notescamera_daggermvvm.vm

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.notescamera_daggermvvm.db.NoteDao
import com.example.notescamera_daggermvvm.db.NoteDatabase
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotSame
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

internal class NoteDaoTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var noteDatabase: NoteDatabase
    private lateinit var noteDao: NoteDao

    @Before
    @Throws(IOException::class)
    fun initDb (){
        val context = ApplicationProvider.getApplicationContext<Context>()
        noteDatabase = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDao = noteDatabase.noteDao()
    }

    @After
    @Throws
    fun closeDb(){
        noteDatabase.close()
    }

//    @BeforeEach
//    fun clearDb(){
//        noteDao.deleteAllnotes()
//    }

    @Test
    fun checkForEmptyDb() {
        noteDao.deleteAllnotes()
        assertEquals("size of empty DB ",noteDao.getAllNotes().getOrAwaitValue().size, 0)
    }

    @Test
    fun insertAndRead() {
        noteDao.deleteAllnotes()
        noteDao.insert(TestUtil.createNote("title","descr",0))
        assertEquals("inserted size ",noteDao.getAllNotes().getOrAwaitValue().size, 1)
        assertEquals("inserted description ",noteDao.getAllNotes().getOrAwaitValue()[0].descr, "descr")
        assertEquals("inserted title ",noteDao.getAllNotes().getOrAwaitValue()[0].title, "title")
        assertEquals("inserted priority ",noteDao.getAllNotes().getOrAwaitValue()[0].prio, 0)
    }

    @Test
    fun insertMany() {
        noteDao.deleteAllnotes()
        for (k in TestUtil.createNotes(4,"title","descr")) {
            noteDao.insert(k)
        }
        assertEquals("inserted quality ",noteDao.getAllNotes().getOrAwaitValue().size, 4)
    }

    @Test
    fun deleteAllNotes() {
        noteDao.deleteAllnotes()
        assertEquals("size of empty DB ",noteDao.getAllNotes().getOrAwaitValue().size, 0)
        noteDao.insert(TestUtil.createNote("title","descr",0))
        assertEquals("inserted size ",noteDao.getAllNotes().getOrAwaitValue().size, 1)
        noteDao.deleteAllnotes()
        assertEquals("size for cleared DB ",noteDao.getAllNotes().getOrAwaitValue().size, 0)
    }

}
