package vch.proj.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import vch.proj.entities.NoteModel

@Dao
interface NoteDao {
    @Query("SELECT id, title, content, date_created, date_updated FROM notes ORDER BY id DESC")
    fun getNotes(): Flow<List<NoteModel>>

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun insert(note: NoteModel)

    @Delete
    suspend fun delete(note: NoteModel)

    @Update
    suspend fun update(note: NoteModel)

    @Query("DELETE FROM notes")
    suspend fun deleteAll()
}