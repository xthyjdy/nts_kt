package vch.proj.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes")
data class NoteModel(
    @ColumnInfo(name = "title") var title: String = "def_titile",
    @ColumnInfo(name = "content") var content: String = "def_content",
    @ColumnInfo(name = "date_created") val dateCreated: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "date_updated") var dateUpdated: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id: Long = 0) : Serializable