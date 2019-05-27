package com.ameyapps.notes.activities


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.ameyapps.notes.R
import com.ameyapps.notes.database.NotesDbHelper
import com.ameyapps.notes.model.NoteInfo
import com.ameyapps.notes.utils.Const
import com.ameyapps.notes.utils.Log
import com.ameyapps.notes.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Context
import android.view.inputmethod.InputMethodManager


class NewNoteActivity : AppCompatActivity() {

    companion object {
        val TAG = "NotesApp " + NewNoteActivity::class.java.simpleName
        var selectedColor: Int = -1
    }

    private var noteRootLinLayout: LinearLayout? = null
    private var bottomSheetBehavior: BottomSheetBehavior<RelativeLayout>? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_note)
        Log.d(TAG, "Create new note")

        val titleEditText = findViewById<EditText>(R.id.edittext_note_title)
        val descpEditText = findViewById<EditText>(R.id.edittext_note_description)
        val saveButton = findViewById<ImageView>(R.id.button_save)
        val moreButton = findViewById<ImageView>(R.id.button_more)
        val bottomSheetLayout = findViewById<RelativeLayout>(R.id.bottom_sheet_rel_layout)
        val linearLayout = findViewById<LinearLayout>(R.id.color_linear_layout)
        noteRootLinLayout = findViewById<LinearLayout>(R.id.note_root_layout)
        val copyTextView = findViewById<TextView>(R.id.text_copy)
        val deleteTextView = findViewById<TextView>(R.id.text_delete)
        val shareTextView = findViewById<TextView>(R.id.text_share)
        val textTime = findViewById<TextView>(R.id.text_time)

        copyTextView.typeface = Const.robotoRegularTf
        deleteTextView.typeface = Const.robotoRegularTf
        shareTextView.typeface = Const.robotoRegularTf
        titleEditText.typeface = Const.robotoRegularTf
        descpEditText.typeface = Const.robotoLightTf
        textTime.typeface = Const.robotoLightTf

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)

        bottomSheetLayout.visibility = View.VISIBLE
        generateCircularColorLayout(linearLayout)

        val notesDbHelper = NotesDbHelper(this@NewNoteActivity)
        /*if (bottomSheetLayout.visibility == View.VISIBLE) {
            bottomSheetLayout.visibility = View.GONE
        } else {
            bottomSheetLayout.visibility = View.VISIBLE
            generateCircularColorLayout(linearLayout)
        }*/


        saveButton.setOnClickListener() {
            val noteInfo = NoteInfo()
            noteInfo.id = Random.nextInt()
            noteInfo.title = titleEditText.text.toString()
            noteInfo.description = descpEditText.text.toString()
            noteInfo.date = Utils.getDate()
            noteInfo.color = selectedColor
            notesDbHelper.addNote(noteInfo)
            Log.d(
                TAG,
                "Note saved: ${noteInfo.id}, ${noteInfo.title}, ${noteInfo.description}, ${noteInfo.date}, ${noteInfo.color}"
            )
            showSnackBar(it, this.resources.getString(R.string.label_note_saved))

        }

        moreButton.setOnClickListener() {
            if (bottomSheetBehavior?.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        copyTextView.setOnClickListener() {
            val noteInfo = NoteInfo()
            noteInfo.id = Random.nextInt()
            noteInfo.title = titleEditText.text.toString()
            noteInfo.description = descpEditText.text.toString()
            noteInfo.date = Utils.getDate()
            noteInfo.color = selectedColor
            notesDbHelper.addNote(noteInfo)
            showSnackBar(it, this.resources.getString(R.string.label_note_copied))
        }

        shareTextView.setOnClickListener() {
            Log.d(ViewNoteActivity.TAG, "Sharing notes")
            val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, titleEditText?.text.toString())
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, descpEditText?.text.toString())
            startActivity(Intent.createChooser(sharingIntent, this.resources.getString(R.string.label_share_via)))
        }

        deleteTextView.setOnClickListener() {
            showSnackBar(it, this.resources.getString(R.string.label_note_discard))
        }

    }


    @SuppressLint("NewApi")
    private fun generateCircularColorLayout(linearLayout: LinearLayout) {

        val ta = resources.obtainTypedArray(R.array.color_array)
        val colorArray = IntArray(ta.length())

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
            colorArray[i] = ta.getColor(i, 0)
            Log.d(TAG, "color int: ${colorArray[i]}")
            bgShape.setColor(colorArray[i])
            linearLayout.addView(imageButton)

            imageButton.setOnClickListener() {
                Log.d(TAG, "Click item position: $i")
                if (noteRootLinLayout != null) {
                    selectedColor = colorArray[i]
                    Log.d(TAG, "Selected Color: $selectedColor")
                    noteRootLinLayout?.setBackgroundColor(selectedColor!!)
                }
            }
        }
    }

    private fun showSnackBar(it: View, message: String) {
        val snackbar = Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
        snackbar.show()
        snackbar.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                Log.d(TAG, "Snackbar dismissed")
                val returnIntent = Intent()
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        })
    }
}