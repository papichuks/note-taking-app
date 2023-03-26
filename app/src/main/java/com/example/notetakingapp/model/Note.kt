package com.example.notetakingapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val noteTitle: String,
    val noteBody: String,
    val uId: String? = null,
    @ColumnInfo(name = "timeStamp", defaultValue = "MMM d, yyyy")val timeStamp: String,
    val label: String? = null
//    val noteImage: String?
) : Parcelable
