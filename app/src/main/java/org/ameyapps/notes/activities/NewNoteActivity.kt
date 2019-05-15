package org.ameyapps.notes.activities

import android.annotation.SuppressLint
import android.content.res.TypedArray
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

import android.graphics.drawable.GradientDrawable


import org.ameyapps.notes.utils.FontsManager


class NewNoteActivity : AppCompatActivity() {

    companion object {
        val TAG = "NotesApp " + NewNoteActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        val robotoLightTf = FontsManager().getRobotoLightFont(this@NewNoteActivity)
        val robotoRegularTf = FontsManager().getRobotoRegularFont(this@NewNoteActivity)

        val titleEditText = findViewById<EditText>(R.id.edittext_note_title)
        val descpEditText = findViewById<EditText>(R.id.edittext_note_description)
        val saveButton = findViewById<ImageButton>(R.id.button_save)
        val moreButton = findViewById<ImageButton>(R.id.button_more)
        val bottomSheetLayout = findViewById<RelativeLayout>(R.id.bottom_sheet_rel_layout)
        val linearLayout = findViewById<LinearLayout>(R.id.color_linear_layout)
        val noteRootLinLayout = findViewById<LinearLayout>(R.id.note_root_layout)
        val copyTextView = findViewById<TextView>(R.id.text_copy)
        val deleteTextView = findViewById<TextView>(R.id.text_delete)
        val shareTextView = findViewById<TextView>(R.id.text_share)
        val textTime = findViewById<TextView>(R.id.text_time)

        copyTextView.typeface = robotoRegularTf
        deleteTextView.typeface = robotoRegularTf
        shareTextView.typeface = robotoRegularTf
        titleEditText.typeface = robotoRegularTf
        descpEditText.typeface = robotoLightTf
        textTime.typeface = robotoRegularTf

        bottomSheetLayout.visibility = View.GONE

        val notesDbHelper = NotesDbHelper(this@NewNoteActivity)

        val ta = resources.obtainTypedArray(R.array.color_array)
        val colorArray = IntArray(ta.length())

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
                generateCircularColorLayout(linearLayout, colorArray, ta)
            }
        }

    }


    @SuppressLint("NewApi")
    private fun generateCircularColorLayout(linearLayout: LinearLayout, colorArray: IntArray, ta: TypedArray) {

        if (linearLayout != null) {
            linearLayout.removeAllViews();
        }
        Log.d(TAG, "Array size: " + colorArray.size + "  item : " + colorArray[1])
        val circleSize = resources.getDimensionPixelSize(R.dimen.circle_size)
        Log.d(TAG, "CircleSize: $circleSize")
        val circleMargin = resources.getDimensionPixelSize(R.dimen.circle_margin)

        for (i in 0 until ta.length()) {

            val imageButton = ImageButton(this@NewNoteActivity)
            imageButton.id = i
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(circleSize, circleSize)
            params.setMargins(0, 0, circleMargin, circleMargin)
            imageButton.layoutParams = params
            imageButton.background = resources.getDrawable(R.drawable.circular_button_backg, null)
            val bgShape = imageButton.background as GradientDrawable
            colorArray[i] = ta.getColor(i, 0);
            bgShape.setColor(colorArray[i])
            linearLayout.addView(imageButton)

            imageButton.setOnClickListener() {
                Log.d(TAG, "Click item position: $i")
            }
        }
    }
}