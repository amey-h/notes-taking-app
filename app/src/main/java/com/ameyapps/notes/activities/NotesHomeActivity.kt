package com.ameyapps.notes.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ameyapps.notes.R
import com.ameyapps.notes.adapter.NotesRecyclerAdapter
import com.ameyapps.notes.database.NotesDbHelper
import com.ameyapps.notes.utils.Const
import com.ameyapps.notes.utils.FontsManager
import com.ameyapps.notes.utils.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton as FAB

class NotesHomeActivity : AppCompatActivity() {

    var TAG = "NotesApp " + NotesHomeActivity::class.java.simpleName
    private var COLUMN_COUNT: Int = 2
    private var recyclerView: RecyclerView? = null
    private var emptyMsgTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar?.title = this.resources.getString(R.string.label_notes)

        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val fab = findViewById<FAB>(R.id.fab)
        emptyMsgTextView = findViewById<TextView>(R.id.text_emptymsg)
        Const.robotoLightTf = FontsManager().getRobotoLightFont(this@NotesHomeActivity)
        Const.robotoRegularTf = FontsManager().getRobotoRegularFont(this@NotesHomeActivity)
        //Const.robotoThintf = FontsManager().getRobotoThinFont(this@NotesHomeActivity)

        displayListView()

        fab.setOnClickListener {
            val intent = Intent(this@NotesHomeActivity, NewNoteActivity::class.java)
            startActivityForResult(intent, Const.NEW_NOTE_REQCODE)
        }
    }

    private fun displayListView() {

        Log.d(TAG, "Displaying ListView")
        val dbHelper = NotesDbHelper(this@NotesHomeActivity)
        val notesInfoList = dbHelper.getAllNotes()

        if (notesInfoList.isEmpty()) {
            Log.d(TAG, "Notes list is empty")
            recyclerView?.visibility = View.GONE
            emptyMsgTextView?.visibility = View.VISIBLE
            emptyMsgTextView?.typeface = Const.robotoRegularTf
        } else {
            Log.d(TAG, "notesList size: " + notesInfoList.size)
            recyclerView?.visibility = View.VISIBLE
            emptyMsgTextView?.visibility = View.GONE
            val notesAdapter = NotesRecyclerAdapter(this@NotesHomeActivity, notesInfoList)
            val staggeredGridLayoutMgr = StaggeredGridLayoutManager(COLUMN_COUNT, LinearLayoutManager.VERTICAL)
            recyclerView?.layoutManager = staggeredGridLayoutMgr;
            recyclerView?.adapter = notesAdapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult called.")
        if (requestCode == Const.NEW_NOTE_REQCODE) {
            if (resultCode == Activity.RESULT_OK) {
                displayListView()
            }
        }
    }
}
