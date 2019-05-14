package org.ameyapps.notes.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.ameyapps.notes.R
import org.ameyapps.notes.database.NotesDbHelper
import org.ameyapps.notes.model.NoteInfo
import java.text.DateFormat
import java.util.*

class NewNoteActivity : AppCompatActivity() {

    companion object {
        val TAG = NewNoteActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        val titleEditText = findViewById<EditText>(R.id.edittext_note_title)
        val descpEditText = findViewById<EditText>(R.id.edittext_note_description)
        val saveButton = findViewById<ImageButton>(R.id.button_save)
        val moreButton = findViewById<ImageButton>(R.id.button_more)
        val bottomSheetLayout = findViewById<RelativeLayout>(R.id.bottom_sheet_rel_layout)
        val linearLayout = findViewById<LinearLayout>(R.id.color_linear_layout)

        bottomSheetLayout.visibility = View.GONE


        val notesDbHelper = NotesDbHelper(this@NewNoteActivity)

        saveButton.setOnClickListener() {
            val noteInfo = NoteInfo()
            noteInfo.id = 1
            noteInfo.title = titleEditText.text.toString()
            noteInfo.description = descpEditText.text.toString()
            noteInfo.date = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
            notesDbHelper.addNote(noteInfo)
            Log.d(TAG, "Note saved")
        }

        moreButton.setOnClickListener() {
            if (bottomSheetLayout.visibility == View.VISIBLE) {
                bottomSheetLayout.visibility = View.GONE
            } else {
                bottomSheetLayout.visibility = View.VISIBLE
                generateCircularColorLayout(linearLayout)
            }
        }


    }

    @SuppressLint("NewApi")
    private fun generateCircularColorLayout(linearLayout: LinearLayout) {

        if (linearLayout != null) {
            linearLayout.removeAllViews();
        }

        val circleSize = resources.getDimensionPixelSize(R.dimen.circle_size)
        Log.d(TAG, "CircleSize: $circleSize")

        val circleMargin = resources.getDimensionPixelSize(R.dimen.circle_size)

        for (i in 0..3) {
            val imageButton = ImageButton(this@NewNoteActivity)
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(circleSize, circleSize)
            params.setMargins(0, 0, circleMargin, circleMargin)
            imageButton.layoutParams = params
            imageButton.background = resources.getDrawable(R.drawable.circular_button_backg, null)
            imageButton.id = i
            linearLayout.addView(imageButton)
        }
    }
}