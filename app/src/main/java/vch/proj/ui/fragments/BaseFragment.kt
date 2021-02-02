package vch.proj.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import vch.proj.di.NotesApplication
import vch.proj.viewmodels.NotesViewModel
import vch.proj.viewmodels.NotesViewModelFactory

/**
 * Base Fragment - fragment for set requires handles
 */
abstract class BaseFragment : Fragment() {
    //view model for Notes Models
    protected val viewModel: NotesViewModel by viewModels() {
        NotesViewModelFactory((activity?.application as NotesApplication).repository)
    }
}