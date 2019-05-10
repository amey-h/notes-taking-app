package org.ameyapps.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.ameyapps.notes.activities.NewNoteActivity
import org.ameyapps.notes.adapter.NotesRecyclerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton as FAB

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FAB>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewNoteActivity::class.java)
            startActivity(intent)
        }

        val recylerView = findViewById<RecyclerView>(R.id.recycler_view);
        val notesAdapter = NotesRecyclerAdapter(this@MainActivity)

        val gridLayoutManager = GridLayoutManager(this@MainActivity, 2);

        recylerView.setLayoutManager(gridLayoutManager);
        recylerView.adapter = notesAdapter
    }
}
