package vch.proj.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import vch.proj.entities.NoteModel
import vch.proj.repositories.NotesRepository
import java.lang.IllegalArgumentException

/**
 * Notes View Model
 */
class NotesViewModel(private val repository: NotesRepository) : ViewModel() {
    val notes: LiveData<List<NoteModel>> = repository.notes.asLiveData()

    fun insert(note: NoteModel) = viewModelScope.launch{
        repository.insert(note)
    }

    fun delete(note: NoteModel) = viewModelScope.launch{
        repository.delete(note)
    }

    fun update(note: NoteModel) = viewModelScope.launch{
        repository.update(note)
    }

    fun deleteAll() = viewModelScope.launch{
        repository.deleteAll()
    }
}

/**
 * Notes View Model Factory - for generate main app view model
 */
class NotesViewModelFactory(private val repository: NotesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}