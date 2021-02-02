package vch.proj.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import vch.proj.R
import vch.proj.entities.NoteModel
import vch.proj.ui.activities.NotesActivity
import vch.proj.helpers.Helper.Companion.l

/**
 * Save Note Fragment - fragment for save/create Note Models
 */
class SaveNoteFragment : BaseFragment() {
    private lateinit var currentNote: NoteModel
    private var actionAdd: Boolean = false
    private val ARG_NOTE = "ARG_NOTE"

    companion object {
        /**
         * New Instance - method which return tuned SaveNoteFragment
         * @param note - instance of Note Model
         * @return instance of SaveNoteFragment
         */
        fun newInstance(note: NoteModel?): SaveNoteFragment {
            val fragment = SaveNoteFragment()
            if (null != note) {
                val args = Bundle()
                args.putSerializable(SaveNoteFragment().ARG_NOTE, note)
                fragment.arguments = args
            }
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //check arguments and specify how fragment will be used (save/update action)
        if (null == arguments?.get(SaveNoteFragment().ARG_NOTE)) {
            actionAdd = true
            currentNote = NoteModel()
        } else {
            currentNote = arguments?.get(SaveNoteFragment().ARG_NOTE) as NoteModel
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.save_note_fragment, container, false)
        val saveNoteText = "Save Note"
        var noteTitle: EditText = view.findViewById(R.id.note_title);
        var noteContent: EditText = view.findViewById(R.id.note_message);
        val saveNote: Button = view.findViewById(R.id.save_note)

        saveNote.text = saveNoteText
        if (!actionAdd) {
            saveNote.text = saveNoteText.replace("Save ", "Update ")
            noteTitle.setText(currentNote.title)
            noteContent.setText(currentNote.content)
        }

        saveNote.setOnClickListener {
            save(noteTitle, noteContent)
        }

        val back: ImageView = view.findViewById(R.id.back)
        back.setOnClickListener {
            actionBack()
        }

        return view
    }

    /**
     * Save - method which save/update Note Model
     * @param newTitle - instance of EditText
     * @param newContent - instance of EditText
     */
    private fun save(newTitle: EditText, newContent: EditText) {
        if (newTitle.text.isEmpty()) {
            Snackbar.make(requireView(),
                    requireContext().resources.getString(R.string.please_fill_title),
                    Snackbar.LENGTH_LONG).show()
            return
        }
        if (newContent.text.isEmpty()) {
            Snackbar.make(requireView(),
                    requireContext().resources.getString(R.string.please_fill_content),
                    Snackbar.LENGTH_LONG).show()
            return
        }
        val placeholder = "%action%"
        var action = ""
        var message = requireContext().resources.getString(R.string.note_was_handled)

        currentNote.apply {
            title = newTitle.text.toString()
            content = newContent.text.toString()
            dateUpdated = System.currentTimeMillis()
        }

        if (actionAdd) {
            action = "Added"
            message = message.replace(placeholder, action)
            viewModel.insert(currentNote)
        } else {
            action = "Updated"
            message = message.replace(placeholder, action)
            viewModel.update(currentNote)
        }

        Snackbar.make(requireView(),
                message,
                Snackbar.LENGTH_LONG).show()
        actionBack()
    }

    /**
     * Action Back - method to exit save/edit mode (from SaveNoteFragment to NotesFragment)
     */
    private fun actionBack() {
        (activity as NotesActivity).actionBack()
    }
}