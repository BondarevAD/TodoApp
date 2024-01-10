package com.example.todoapp

import com.example.todoapp.data.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

class TodoItemsRepository {
    val todos = mutableListOf(
        TodoItem("Shopping", true, Date(), Date(), true),
        TodoItem("ReadBook", false, Date(), null, true),
        TodoItem("Exercise", true, Date(), Date(), true),
        TodoItem("Learn Kotlin", false, Date(), null, true),
        TodoItem("Write Blog Post", true, Date(), Date(), true),
        TodoItem("Clean House", false, Date(), null, true),
        TodoItem("Plan Vacation", true, Date(), Date(), true),
        TodoItem("Study New Language", false, Date(), null, true),
        TodoItem("Complete Project", true, Date(), Date(), true),
        TodoItem("Organize Workspace", false, Date(), null, true),
        TodoItem("Cook Dinner", true, Date(), Date(), true),
        TodoItem("Learn Guitar", false, Date(), null, true),
        TodoItem("Painting", true, Date(), Date(), true),
        TodoItem("Gardening", false, Date(), null, true),
        TodoItem("Write Poem", true, Date(), Date(), true),
        TodoItem("Volunteer Work", false, Date(), null, true),
        TodoItem("Watch Movie", true, Date(), Date(), true),
        TodoItem("Run Marathon", false, Date(), null, true),
        TodoItem("Code Project", true, Date(), Date(), true),
        TodoItem("Yoga Session", false, Date(), null, true)
    )

    fun addTodo(item: TodoItem) {
        todos.add(item)
    }

}