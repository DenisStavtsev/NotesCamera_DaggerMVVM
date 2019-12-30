package com.example.notescamera_daggermvvm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast

class NewOrEditNoteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE: String = "com.example.notescamera_daggermvvm.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION: String = "com.example.notescamera_daggermvvm.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY: String = "com.example.notescamera_daggermvvm.EXTRA_PRIORITY"
        const val EXTRA_ID: String = "com.example.notescamera_daggermvvm.EXTRA_ID"
    }

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var numberPickerPriority: NumberPicker

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        editTextTitle = findViewById(R.id.edit_text_title)
        editTextDescription = findViewById(R.id.edit_text_description)
        numberPickerPriority = findViewById(R.id.number_picker_priority)
        numberPickerPriority.minValue = 0
        numberPickerPriority.maxValue = 5

        if (intent.hasExtra(EXTRA_ID)) {
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE))
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            numberPickerPriority.value = intent.getIntExtra(EXTRA_PRIORITY,0)

            title = "Edit Note"
        } else title = "Add Note"

    }

    private fun saveNote(){
        val title = editTextTitle.text.toString()
        val description = editTextDescription.text.toString()
        val priority = numberPickerPriority.value

        if (title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this,"Please insert Title and Description",Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent()
        data.putExtra(EXTRA_TITLE,title)
        data.putExtra(EXTRA_DESCRIPTION,description)
        data.putExtra(EXTRA_PRIORITY,priority)

        val checkID = intent.getIntExtra(EXTRA_ID, -1)
        if ( checkID != -1) {
            data.putExtra(EXTRA_ID,checkID)
        }

        setResult(Activity.RESULT_OK,data)
        finish()

    }

    override fun onCreateOptionsMenu( menu: Menu ):Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

}
