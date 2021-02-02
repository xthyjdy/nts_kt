package vch.proj.database

import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import vch.proj.dao.NoteDao
import vch.proj.entities.NoteModel

@Database(entities = arrayOf(NoteModel::class), version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var dao = database.getNoteDao()

                    // Delete all content here.
                    dao.deleteAll()

                    // Add sample notes
                    var note = NoteModel(title = "n1")
                    dao.insert(note)
                    note = NoteModel(title = "n2")
                    dao.insert(note)
                    note = NoteModel(title = "n3")
                    dao.insert(note)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): NotesDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "notes_db_v1"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}