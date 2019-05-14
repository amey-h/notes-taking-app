package org.ameyapps.notes.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.ameyapps.notes.R
import org.ameyapps.notes.database.NotesDbHelper
import org.ameyapps.notes.model.NoteInfo
import java.text.DateFormat
import java.util.*

class NewNoteActivity: AppCompatActivity() {

    companion object {
        val TAG = NewNoteActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        val titleEditText = findViewById<EditText>(R.id.edittext_note_title)
        val descpEditText = findViewById<EditText>(R.id.edittext_note_description)
        val saveButton = findViewById<Button>(R.id.button_save)

        val notesDbHelper =  NotesDbHelper(this@NewNoteActivity)

        saveButton.setOnClickListener() {
            val noteInfo = NoteInfo()
            noteInfo.id = 1
            noteInfo.title = titleEditText.text.toString()
            noteInfo.description = descpEditText.text.toString()
            noteInfo.date = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
            notesDbHelper.addNote(noteInfo)
            Log.d(TAG, "Note saved")
        }

    }
}