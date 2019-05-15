package org.ameyapps.notes

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
import com.google.android.material.snackbar.Snackbar
import org.ameyapps.notes.activities.NewNoteActivity
import org.ameyapps.notes.adapter.NotesRecyclerAdapter
import org.ameyapps.notes.database.NotesDbHelper
import org.ameyapps.notes.model.NoteInfo
import org.ameyapps.notes.utils.FontsManager
import com.google.android.material.floatingactionbutton.FloatingActionButton as FAB

class MainActivity : AppCompatActivity() {

    var TAG = MainActivity::class.java.simpleName
    private var COLUMN_COUNT: Int = 2
    private var robotoLightTf: Typeface? = null
    private var robotoRegularTf: Typeface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val fab = findViewById<FAB>(R.id.fab)
        val emptyMsgTextView = findViewById<TextView>(R.id.text_emptymsg)
        robotoLightTf = FontsManager().getRobotoLightFont(this@MainActivity)
        robotoRegularTf = FontsManager().getRobotoRegularFont(this@MainActivity)

        val dbHelper = NotesDbHelper(this@MainActivity)
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
            val notesAdapter = NotesRecyclerAdapter(this@MainActivity, notesInfoList)
            val gridLayoutManager = GridLayoutManager(this@MainActivity, COLUMN_COUNT);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.adapter = notesAdapter
        }

        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewNoteActivity::class.java)
            startActivity(intent)
        }
    }
}
