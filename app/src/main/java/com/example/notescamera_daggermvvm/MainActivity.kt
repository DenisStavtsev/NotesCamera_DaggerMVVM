package com.example.notescamera_daggermvvm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notescamera_daggermvvm.db.Note
import com.example.notescamera_daggermvvm.vm.NoteListAdapter
import com.example.notescamera_daggermvvm.vm.NoteViewModel
import com.example.notescamera_daggermvvm.vm.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

//MainActivity << NoteViewModelFactory << NoteViewModel << NoteRepository << noteDao << NoteDataBase << context_application
class MainActivity : DaggerAppCompatActivity(), NoteListAdapter.OnNoteListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var noteViewModel: NoteViewModel
    private val newNoteActivityRequestCode = 1
    private val editNoteActivityRequestCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recycleview)
        val adapter = NoteListAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        noteViewModel = ViewModelProvider(this, viewModelFactory)
            .get(NoteViewModel::class.java)

        noteViewModel.getAllNotes().observe(this, Observer<List<Note>> {
                notes -> notes?.let { adapter.submitList(it) }
            Log.v("MainActivity -> onCreate -> observe", adapter.itemCount.toString())
        })

        val noteTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
            }

        }

        val noteTouchHelper = ItemTouchHelper(noteTouchCallback)
        noteTouchHelper.attachToRecyclerView(recyclerView)

        val buttonAddNote = findViewById<FloatingActionButton>(R.id.add_note)
        buttonAddNote.setOnClickListener {
            val intent = Intent(this@MainActivity,NewOrEditNoteActivity::class.java)
            startActivityForResult(intent,newNoteActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newNoteActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra(NewOrEditNoteActivity.EXTRA_TITLE)
            val description = data?.getStringExtra(NewOrEditNoteActivity.EXTRA_DESCRIPTION)
            val priority = data!!.getIntExtra(NewOrEditNoteActivity.EXTRA_PRIORITY,0)
            val note = Note(null, title, description, priority)

            noteViewModel.insert(note)
            Toast.makeText(this,"Note saved",Toast.LENGTH_SHORT).show()

        } else if (requestCode == editNoteActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(NewOrEditNoteActivity.EXTRA_ID,-1)

            if (id == -1)
                Toast.makeText(
                    applicationContext,
                    R.string.not_updated,
                    Toast.LENGTH_LONG).show()
            else {
                val title = data?.getStringExtra(NewOrEditNoteActivity.EXTRA_TITLE)
                val description = data?.getStringExtra(NewOrEditNoteActivity.EXTRA_DESCRIPTION)
                val priority = data!!.getIntExtra(NewOrEditNoteActivity.EXTRA_PRIORITY,0)
                val note = Note(id, title, description, priority)

                noteViewModel.update(note)
                Toast.makeText(this,"Note updated",Toast.LENGTH_SHORT).show()
            }

        }
        else {
            when (requestCode) {
                editNoteActivityRequestCode -> Toast.makeText( applicationContext, R.string.empty_not_changed, Toast.LENGTH_LONG).show()
                newNoteActivityRequestCode -> Toast.makeText( applicationContext, R.string.empty_not_created, Toast.LENGTH_LONG).show()
                else -> Toast.makeText( applicationContext, "Something has gone wrong", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_delete_all -> {
                noteViewModel.deleteAll()
                Toast.makeText(this,"And it's gone", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNoteClick(note: Note) {
        Log.d("MainActivity -> onNoteClick","click id # ${note.idNumber}")
        val intent = Intent(this@MainActivity,NewOrEditNoteActivity::class.java)
        intent.putExtra(NewOrEditNoteActivity.EXTRA_TITLE,note.title)
        intent.putExtra(NewOrEditNoteActivity.EXTRA_PRIORITY,note.prio)
        intent.putExtra(NewOrEditNoteActivity.EXTRA_DESCRIPTION,note.descr)
        intent.putExtra(NewOrEditNoteActivity.EXTRA_ID,note.idNumber)
        startActivityForResult(intent,editNoteActivityRequestCode)
    }
}
