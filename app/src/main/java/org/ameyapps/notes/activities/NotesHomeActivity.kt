package org.ameyapps.notes.activities

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
import org.ameyapps.notes.utils.FontsManager
import com.google.android.material.floatingactionbutton.FloatingActionButton as FAB

class NotesHomeActivity : AppCompatActivity() {

    var TAG = "NotesApp " + NotesHomeActivity::class.java.simpleName
    private var COLUMN_COUNT: Int = 2
    private var robotoLightTf: Typeface? = null
    private var robotoRegularTf: Typeface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val fab = findViewById<FAB>(R.id.fab)
        val emptyMsgTextView = findViewById<TextView>(R.id.text_emptymsg)
        robotoLightTf = FontsManager().getRobotoLightFont(this@NotesHomeActivity)
        robotoRegularTf = FontsManager().getRobotoRegularFont(this@NotesHomeActivity)

        val dbHelper = NotesDbHelper(this@NotesHomeActivity)
        val notesInfoList = dbHelper.getAllNotes()

        if (notesInfoList.isEmpty()) {
            Log.d(TAG, "Notes list is empty")
            recyclerView.visibility = View.GONE
            emptyMsgTextView.visibility = View.VISIBLE
            emptyMsgTextView.typeface = robotoRegularTf
        } else {
            Log.d(TAG, "notesList size: " + notesInfoList.size)
            recyclerView.visibility = View.VISIBLE
            emptyMsgTextView.visibility = View.GONE
            val notesAdapter = NotesRecyclerAdapter(this@NotesHomeActivity, notesInfoList)
            //val gridLayoutManager = GridLayoutManager(this@NotesHomeActivity, COLUMN_COUNT);
            val staggeredGridLayoutMgr = StaggeredGridLayoutManager(COLUMN_COUNT, LinearLayoutManager.VERTICAL)
            recyclerView.layoutManager = staggeredGridLayoutMgr;
            recyclerView.adapter = notesAdapter
        }

        fab.setOnClickListener {
            val intent = Intent(this@NotesHomeActivity, NewNoteActivity::class.java)
            startActivity(intent)
        }
    }
}
