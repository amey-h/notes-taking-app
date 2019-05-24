package com.ameyapps.notes.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ameyapps.notes.R
import com.ameyapps.notes.activities.ViewNoteActivity
import com.ameyapps.notes.model.NoteInfo
import com.ameyapps.notes.utils.Const
import com.ameyapps.notes.utils.Log

class NotesViewHolder(inflater: LayoutInflater, parent: ViewGroup, var context: Activity) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.recycler_view_items_layout, parent, false)),
    View.OnClickListener {

    companion object {
        val TAG = "NotesApp " + NotesViewHolder::class.java.simpleName
    }
    private var titleTextView: TextView? = null
    private var descriptionTextView: TextView? = null
    private var cardRootLinLayout: LinearLayout? = null
    private var cardView: CardView? = null
    private var mNoteInfo: NoteInfo? = null

    init {
        titleTextView = itemView.findViewById(R.id.title_text)
        descriptionTextView = itemView.findViewById(R.id.description_text)
        cardRootLinLayout = itemView.findViewById(R.id.carditem_root_view)
        cardView = itemView.findViewById(R.id.card_view)
        itemView.setOnClickListener(this)
    }

    fun bind(noteInfo: NoteInfo) {

        mNoteInfo = noteInfo
        mNoteInfo?.id = noteInfo.id
        mNoteInfo?.title = noteInfo.title
        mNoteInfo?.description = noteInfo.description
        mNoteInfo?.date = noteInfo.date
        mNoteInfo?.color = noteInfo.color

        titleTextView?.setText(noteInfo.title);
        titleTextView?.typeface = Const.robotoRegularTf

        descriptionTextView?.setText(noteInfo.description)
        descriptionTextView?.typeface = Const.robotoLightTf

        if (noteInfo.color != 1 || noteInfo.color != -1) {
            val colorToUse = noteInfo.color
            cardRootLinLayout?.setBackgroundColor(colorToUse!!)
            cardView?.setBackgroundColor(colorToUse!!)
        } else {
            cardRootLinLayout?.setBackgroundColor(context.resources.getColor(R.color.background))
            cardView?.setBackgroundColor(context.resources.getColor(R.color.background))
        }
    }

    override fun onClick(v: View?) {

        Log.d(TAG, "Item clicked Adapterpos1: $adapterPosition")
        val intent = Intent(context, ViewNoteActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(Const.NOTE_INFO_KEY, mNoteInfo)
        intent.putExtras(bundle)
        context.startActivityForResult(intent, Const.NEW_NOTE_REQCODE)

    }
}