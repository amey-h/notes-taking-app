package com.ameyapps.notes.activities

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ameyapps.notes.R
import com.ameyapps.notes.adapter.NotesRecyclerAdapter
import com.ameyapps.notes.database.NotesDbHelper
import com.ameyapps.notes.model.NoteInfo
import com.ameyapps.notes.utils.Const
import com.ameyapps.notes.utils.FontsManager
import com.ameyapps.notes.utils.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton as FAB




class NotesHomeActivity : AppCompatActivity() {

    var TAG = "NotesApp " + NotesHomeActivity::class.java.simpleName
    private var COLUMN_COUNT: Int = 2
    private var recyclerView: RecyclerView? = null
    private var emptyMsgTextView: TextView? = null
    private var mNoteInfoList: ArrayList<NoteInfo>? = null

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
        mNoteInfoList = ArrayList()
        displayListView()

        fab.setOnClickListener {
            val intent = Intent(this@NotesHomeActivity, NewNoteActivity::class.java)
            startActivityForResult(intent, Const.NEW_NOTE_REQCODE)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        // Associate searchable configuration with the SearchView
        /*val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.search)?.actionView as SearchView).apply {
            Log.d(TAG, "Calling Search.")
            this.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }*/
        Log.d(TAG, "Calling Search.")
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(componentName)
        )
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "search q: $query")

                for(noteInfo in mNoteInfoList!!) {
                    if(noteInfo.description!!.contains(query!!)) {
                        Log.d(TAG, "Found search: $query")
                    }
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "search newText: $newText")
                return false
            }
        })
        return true
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
            mNoteInfoList = notesInfoList
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
