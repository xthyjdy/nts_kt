package vch.proj.ui.activities

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import vch.proj.R
import vch.proj.entities.NoteModel
import vch.proj.helpers.Helper.Companion.l
import vch.proj.ui.fragments.NotesFragment
import vch.proj.ui.fragments.SaveNoteFragment

/**
 * Main Recycle View Activity
 */
class NotesActivity : BaseActivity() {
    private var mainFragment: Fragment = NotesFragment()

    companion object {
        //vars using for changing LayoutManager (in NotesFragment)
        var fragmentFirstBoot = false
        var view = ""
        //

        /**
         * New Intent - return Intent with current activity(there can be specified additional settings)
         * @param context instance of @{@link Context}
         * @return Intent instance of @{@link Intent}
         */
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, NotesActivity::class.java)

            return intent
        }
    }

    override fun setFragment() =  mainFragment

    override fun init() {
        super.init()
    }

    /**
     * Save Note - method for save/update Note Model
     * @param note - instance of NoteModel
     */
    fun saveNote(note: NoteModel?) {
        mainFragment = SaveNoteFragment.newInstance(note)
        changeFragment(mainFragment)
    }

    /**
     * Action Back - method to exit save/edit mode (from SaveNoteFragment to NotesFragment)
     */
    fun actionBack() {
        mainFragment = NotesFragment()
        changeFragment(mainFragment)
    }
}