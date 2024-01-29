package com.example.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.UUID

@Entity(tableName = "todos")
data class TodoItem(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val text: String,
    var isDone: Boolean,
    val creationDate: LocalDate,
    val deadline: LocalDate? = null,
    val changeDate: LocalDate,
    val importance: Boolean
)


