package org.ameyapps.notes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.ameyapps.notes.model.NoteInfo

class NotesRecyclerAdapter(val context: Context): RecyclerView.Adapter<NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(context);
        return NotesViewHolder(inflater, parent, context);
    }

    override fun getItemCount(): Int {
        return 10;
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val noteInfo = NoteInfo();
        holder.bind(noteInfo)
    }
}