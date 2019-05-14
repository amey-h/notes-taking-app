package org.ameyapps.notes.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.ameyapps.notes.R
import org.ameyapps.notes.model.NoteInfo
import org.ameyapps.notes.utils.FontsManager

class NotesViewHolder(inflater: LayoutInflater, parent: ViewGroup, var context: Context) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.recycler_view_items_layout, parent, false)) {

    var titleTextView: TextView? = null
    var descriptionTextView: TextView? = null
    var robotoLightTf: Typeface? = null
    var robotoRegularTf: Typeface? = null

    init {
        titleTextView = itemView.findViewById(R.id.title_text)
        descriptionTextView = itemView.findViewById(R.id.description_text)

        robotoLightTf = FontsManager().getRobotoLightFont(context = context)
        robotoRegularTf = FontsManager().getRobotoRegularFont(context = context)
    }

    fun bind(noteInfo: NoteInfo) {

        titleTextView?.setText(noteInfo.title);
        titleTextView?.typeface = robotoRegularTf

        descriptionTextView?.setText(noteInfo.description)
        descriptionTextView?.typeface = robotoLightTf
    }

}