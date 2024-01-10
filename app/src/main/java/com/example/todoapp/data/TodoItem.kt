package com.example.todoapp.data

import java.util.Date

data class TodoItem(
    val id: String,
    val isDone: Boolean,
    val creationDate: Date,
    val editDate: Date?,
    val priority: Boolean
)
