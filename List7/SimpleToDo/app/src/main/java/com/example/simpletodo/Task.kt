package com.example.simpletodo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "task")
data class Task(
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "category") val category: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    @ColumnInfo(name = "done")
    var done = false
}