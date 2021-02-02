package vch.proj.ui.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import vch.proj.R
import vch.proj.entities.NoteModel
import vch.proj.helpers.Helper.Companion.l
import vch.proj.ui.activities.NotesActivity
import vch.proj.ui.adapters.NotesAdapter

class NotesFragment() : BaseFragment(), NotesAdapter.NotesClickListener {
    var recyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.notes_fragment, container, false)
        val noteAdapter = NotesAdapter(this)

        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView?.apply {
            adapter = noteAdapter
            setHasFixedSize(true)
        }
        //set default LayoutManaget
        setNoteLayoutManager(null)

        viewModel.notes.observe(viewLifecycleOwner) {
            noteAdapter.submitList(it)
        }

        val fob: FloatingActionButton = view.findViewById(R.id.add_note)
        fob.setOnClickListener {
            (activity as NotesActivity).saveNote(null)
        }

        val popupMenuIV: ImageView = view.findViewById(R.id.notes_page_popap_menu)
        popupMenuIV.setOnClickListener {
            showPopapMenu(it)
        }

        //create swipe action for item
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = noteAdapter.currentList.get(viewHolder.adapterPosition)
                viewModel.delete(note)
                Snackbar.make(requireView(), "${note.title} was deleted", Snackbar.LENGTH_LONG).show()
            }
        }).attachToRecyclerView(recyclerView)

        return view
    }

    /**
     * ShowPopapMenu - method which show/hide main(top bar) menu of app
     * @param v  instance of View
     */
    fun showPopapMenu(v: View) {
        val popupMenu = androidx.appcompat.widget.PopupMenu(requireContext(), v)
        popupMenu.inflate(R.menu.notes_page_top_bar_popup_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.to_list_interface -> {
                    setNoteLayoutManager(LinearLayoutManager(requireContext()));
                    true
                }
                R.id.to_card_interface -> {
                    setNoteLayoutManager(GridLayoutManager(requireContext(), 3))
                    true
                }
                R.id.top_bar_popap_delete_all_notes -> {
                    val adb = AlertDialog.Builder(requireContext())
                    adb.setCancelable(false)
                            .setTitle(requireContext().resources.getString(R.string.adb_delete_all_notes))
                            .setPositiveButton(requireContext().resources.getString(R.string.adb_delete),
                                    DialogInterface.OnClickListener { dialog, which ->
                                        viewModel.deleteAll()
                                        Snackbar.make(requireView(),
                                                requireContext().resources.getString(R.string.adb_deleted_success),
                                                Snackbar.LENGTH_LONG).show()
                                    })
                            .setNegativeButton(requireContext().resources.getString(R.string.adb_cancel),
                                    DialogInterface.OnClickListener { dialog, which ->
                                        dialog.cancel()
                                    })
                    val alertDialog = adb.create()
                    alertDialog.setOnShowListener { dialog: DialogInterface? ->
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                                .setTextColor(requireContext().resources.getColor(R.color.colorRed))
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                                .setTextColor(requireContext().resources.getColor(R.color.colorBlack))
                    }

                    alertDialog.show()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    /**
     * Set Note Layout Manager - method which set specified layout (LinerLayout or GridLayout)
     * @param layoutManager instance of @{@link RecyclerView.LayoutManager}
     */
    private fun setNoteLayoutManager(layoutManager: RecyclerView.LayoutManager?) {
        var layout: RecyclerView.LayoutManager? = null

        if (false == NotesActivity.fragmentFirstBoot) {
            layout = LinearLayoutManager(requireContext())
            NotesActivity.view = layout.javaClass.simpleName
            NotesActivity.fragmentFirstBoot = true
        } else {
            if (null == layoutManager) {
                if (NotesActivity.view.equals(requireContext().resources.getString(R.string.linear_layout_manager))) {
                    layout = LinearLayoutManager(requireContext());
                } else if (NotesActivity.view.equals(requireContext().resources.getString(R.string.grid_layout_manager))) {
                    layout = GridLayoutManager(getActivity(), 3);
                }
            } else {
                layout = layoutManager;
                NotesActivity.view = layout.javaClass.simpleName
            }
        }
        recyclerView?.layoutManager = layout
    }

    override fun onItemClicked(note: NoteModel) {
        (activity as NotesActivity).saveNote(note)
    }
}