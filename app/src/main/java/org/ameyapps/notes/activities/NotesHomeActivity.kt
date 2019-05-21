package org.ameyapps.notes.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.ameyapps.notes.R
import org.ameyapps.notes.adapter.NotesRecyclerAdapter
import org.ameyapps.notes.database.NotesDbHelper
import org.ameyapps.notes.utils.Const
import org.ameyapps.notes.utils.FontsManager
import com.google.android.material.floatingactionbutton.FloatingActionButton as FAB

class NotesHomeActivity : AppCompatActivity() {

    var TAG = "NotesApp " + NotesHomeActivity::class.java.simpleName
    private var COLUMN_COUNT: Int = 2
    private var recyclerView: RecyclerView? = null
    private var emptyMsgTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val fab = findViewById<FAB>(R.id.fab)
        emptyMsgTextView = findViewById<TextView>(R.id.text_emptymsg)
        Const.robotoLightTf = FontsManager().getRobotoLightFont(this@NotesHomeActivity)
        Const.robotoRegularTf = FontsManager().getRobotoRegularFont(this@NotesHomeActivity)

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
