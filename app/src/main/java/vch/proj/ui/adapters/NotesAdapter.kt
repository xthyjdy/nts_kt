package vch.proj.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vch.proj.R
import vch.proj.entities.NoteModel

class NotesAdapter(private val listener: NotesClickListener) : ListAdapter<NoteModel, NotesAdapter.NoteViewHolder>(NotesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)

        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note: NoteModel = getItem(position)
        holder.bind(note)
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.title_text_view)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (RecyclerView.NO_POSITION != position) {
                    val note = getItem(position)
                    listener.onItemClicked(note)
                }
            }
        }

        fun bind(note: NoteModel) {
            titleTextView.text = note.title
        }
    }

    class NotesComparator : DiffUtil.ItemCallback<NoteModel>() {
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel) = oldItem == newItem
    }

    /**
     * Notes Click Listener - interface for handle user item touch
     */
    interface NotesClickListener {
        /**
         * On Item Clicked - method for handle user item touch
         * @param note - instance of NoteModel
         */
        fun onItemClicked(note: NoteModel)
    }
}