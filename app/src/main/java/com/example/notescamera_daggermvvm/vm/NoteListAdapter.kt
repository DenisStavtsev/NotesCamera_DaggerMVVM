package com.example.notescamera_daggermvvm.vm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notescamera_daggermvvm.R
import com.example.notescamera_daggermvvm.db.Note

class NoteListAdapter internal constructor(context: Context, onNoteListener: OnNoteListener)
    : ListAdapter<Note, NoteListAdapter.NoteViewHolder>(
    DiffCallback()
) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val onNoteL = onNoteListener

    class DiffCallback :DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.idNumber == newItem.idNumber
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.title.equals(newItem.title) &&
                    oldItem.descr.equals(newItem.descr) &&
                    oldItem.prio == newItem.prio
        }
    }

    inner class NoteViewHolder(itemView: View,onNoteListener: OnNoteListener): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val noteViewTitle: TextView = itemView.findViewById(R.id.textView_title)
        val noteViewPriority: TextView = itemView.findViewById(R.id.textView_prio)
        val noteViewDescription: TextView = itemView.findViewById(R.id.textView_description)
        private val onNoteListener: OnNoteListener

        init {
            itemView.setOnClickListener(this)
            this.onNoteListener = onNoteListener
        }

        override fun onClick(p0: View?) {
            if(adapterPosition != RecyclerView.NO_POSITION)
                onNoteListener.onNoteClick(getItem(adapterPosition))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = inflater.inflate(R.layout.recycleview_item,parent,false)
        return NoteViewHolder(itemView,onNoteL)
    }

//    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = getItem(position)
        holder.noteViewTitle.text = current.title
        holder.noteViewDescription.text = current.descr
        holder.noteViewPriority.text = current.prio.toString()
    }

    fun getNoteAt (position:Int) : Note {
        return getItem(position)
    }

    interface OnNoteListener {
        fun onNoteClick (note: Note)
    }

}