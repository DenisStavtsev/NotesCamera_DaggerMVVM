package com.example.notescamera_daggermvvm.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase:RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object{

        @Volatile
        private var INSTANCE: NoteDatabase?=null
        private val PREPOPULATE_DATA = Note(null, "Title1", "Description1", 0)

        fun getInstance(context: Context): NoteDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(
                        context
                    ).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                    NoteDatabase::class.java, "Notes.db")
                    .addCallback(object : Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
//                            Executors.newSingleThreadExecutor().execute {
//                                getInstance(context).noteDao().insert(PREPOPULATE_DATA)
//                            }
                            ioThread {
                                Log.v("DataBase -> buildDatabase -> onCreate -> ioThread", "Hello")
                                getInstance(context).noteDao().insert(PREPOPULATE_DATA)
                            }
                        }
                    })
                    .build()
    }
}
