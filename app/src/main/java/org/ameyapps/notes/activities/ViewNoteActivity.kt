package org.ameyapps.notes.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import org.ameyapps.notes.BaseActivity
import org.ameyapps.notes.R
import org.ameyapps.notes.database.NotesDbHelper
import org.ameyapps.notes.model.NoteInfo
import org.ameyapps.notes.utils.Const
import org.ameyapps.notes.utils.Log
import org.ameyapps.notes.utils.Utils
import kotlin.random.Random

class ViewNoteActivity : BaseActivity() {

    companion object {
        val TAG = "NotesApp " + ViewNoteActivity::class.java.simpleName
        var selectedColor: Int = -1
    }

    private var noteRootLinLayout: LinearLayout? = null
    private var mNoteInfo: NoteInfo? = null
    private var titleEditText: TextView? = null
    private var descpEditText: TextView? = null
    private var timeTextView: TextView? = null

    override fun getResourceLayout(): Int {
        return R.layout.activity_view_note
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle: Bundle = this.intent.extras
        if(bundle != null) {
            mNoteInfo = bundle.getSerializable(Const.NOTE_INFO_KEY) as NoteInfo
        }

        noteRootLinLayout = findViewById<LinearLayout>(R.id.vn_note_root_layout)
        titleEditText = findViewById<EditText>(R.id.vn_edittext_note_title)
        descpEditText = findViewById<EditText>(R.id.vn_edittext_note_description)
        timeTextView = findViewById<TextView>(R.id.vn_text_time)

        val saveButton = findViewById<ImageButton>(R.id.vn_button_save)
        val moreButton = findViewById<ImageButton>(R.id.vn_button_more)
        val bottomSheetLayout = findViewById<RelativeLayout>(R.id.vn_bottom_sheet_rel_layout)
        val linearLayout = findViewById<LinearLayout>(R.id.vn_color_linear_layout)
        val copyTextView = findViewById<TextView>(R.id.vn_text_copy)
        val deleteTextView = findViewById<TextView>(R.id.vn_text_delete)
        val shareTextView = findViewById<TextView>(R.id.vn_text_share)

        copyTextView.typeface = Const.robotoRegularTf
        deleteTextView.typeface = Const.robotoRegularTf
        shareTextView.typeface = Const.robotoRegularTf
        titleEditText?.typeface = Const.robotoRegularTf
        descpEditText?.typeface = Const.robotoLightTf
        timeTextView?.typeface = Const.robotoLightTf

        bottomSheetLayout.visibility = View.GONE

        val notesDbHelper = NotesDbHelper(this@ViewNoteActivity)

        if(mNoteInfo != null) {
            Log.d(TAG, "NoteInfo title/descp : ${mNoteInfo?.title}, ${mNoteInfo?.description}")
            selectedColor = mNoteInfo?.color!!
            setDataAndView()
        } else {
           Log.w(TAG, "Error occurred while displaying data.")
        }

        saveButton.setOnClickListener() {
            val noteInfo = NoteInfo()
            noteInfo.id = mNoteInfo?.id!!
            noteInfo.title = titleEditText?.text.toString()
            noteInfo.description = descpEditText?.text.toString()
            noteInfo.date = Utils.getDate()
            noteInfo.color = selectedColor
            notesDbHelper.updateNote(noteInfo)
            Log.d(TAG, "Note updated")
            showSnackBar(it, "Note updated")
        }

        moreButton.setOnClickListener() {
            if (bottomSheetLayout.visibility == View.VISIBLE) {
                bottomSheetLayout.visibility = View.GONE
            } else {
                bottomSheetLayout.visibility = View.VISIBLE
                generateCircularColorLayout(linearLayout)
            }
        }

        copyTextView.setOnClickListener() {
            val noteInfo = NoteInfo()
            noteInfo.id = Random.nextInt()
            noteInfo.title = titleEditText?.text.toString()
            noteInfo.description = descpEditText?.text.toString()
            noteInfo.date = Utils.getDate()
            noteInfo.color = mNoteInfo?.color!!
            notesDbHelper.addNote(noteInfo)
            showSnackBar(it, "Note copied")
        }

        deleteTextView.setOnClickListener() {
            notesDbHelper.deleteNote(mNoteInfo!!)
            showSnackBar(it, "Note deleted")
        }
    }

    private fun setDataAndView() {
        noteRootLinLayout?.setBackgroundColor(mNoteInfo?.color!!)
        titleEditText?.text = mNoteInfo?.title
        descpEditText?.text = mNoteInfo?.description
        timeTextView?.text = "Last edit on ${mNoteInfo?.date}"
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

            val imageButton = ImageButton(this@ViewNoteActivity)
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
                    Log.d(TAG, "Selected Color: ${selectedColor}")
                    noteRootLinLayout?.setBackgroundColor(selectedColor!!)
                }
            }
        }
    }

    private fun showSnackBar(it: View, message: String) {
        val snackbar = Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
        snackbar.show()
        snackbar.addCallback(object :  Snackbar.Callback()  {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                Log.d(NewNoteActivity.TAG, "Snackbar dismissed")
                val returnIntent = Intent()
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        })
    }
}