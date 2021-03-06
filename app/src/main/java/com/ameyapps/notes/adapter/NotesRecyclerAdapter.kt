package com.ameyapps.notes.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ameyapps.notes.model.NoteInfo

class NotesRecyclerAdapter(val context: Activity, val noteInfoList: ArrayList<NoteInfo>): RecyclerView.Adapter<NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(context);
        return NotesViewHolder(inflater, parent, context);
    }

    override fun getItemCount(): Int {
        return noteInfoList.size;
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val noteInfo = noteInfoList.get(position)
        holder.bind(noteInfo)
    }
}