package vch.proj.repositories

import kotlinx.coroutines.flow.Flow
import vch.proj.dao.NoteDao
import vch.proj.entities.NoteModel

class NotesRepository(private val dao: NoteDao) {
    val notes: Flow<List<NoteModel>> = dao.getNotes()

    suspend fun insert(note: NoteModel) = dao.insert(note)

    suspend fun delete(note: NoteModel) = dao.delete(note)

    suspend fun update(note: NoteModel) = dao.update(note)

    suspend fun deleteAll() = dao.deleteAll()
}