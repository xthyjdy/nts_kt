package vch.proj.di

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import vch.proj.database.NotesDatabase
import vch.proj.repositories.NotesRepository

/**
 * Notes Application - inject required di in Application class
 */
class NotesApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { NotesDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { NotesRepository(database.getNoteDao()) }
}