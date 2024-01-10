package com.example.todoapp

import com.example.todoapp.data.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

class TodoItemsRepository {
    private val _todos = MutableStateFlow<MutableList<TodoItem>>(mutableListOf(
        TodoItem("Shopping", true, Date(), Date()),
        TodoItem("ReadBook", false, Date(), null),
        TodoItem("Exercise", true, Date(), Date()),
        TodoItem("Learn Kotlin", false, Date(), null),
        TodoItem("Write Blog Post", true, Date(), Date()),
        TodoItem("Clean House", false, Date(), null),
        TodoItem("Plan Vacation", true, Date(), Date()),
        TodoItem("Study New Language", false, Date(), null),
        TodoItem("Complete Project", true, Date(), Date()),
        TodoItem("Organize Workspace", false, Date(), null),
        TodoItem("Cook Dinner", true, Date(), Date()),
        TodoItem("Learn Guitar", false, Date(), null),
        TodoItem("Painting", true, Date(), Date()),
        TodoItem("Gardening", false, Date(), null),
        TodoItem("Write Poem", true, Date(), Date()),
        TodoItem("Volunteer Work", false, Date(), null),
        TodoItem("Watch Movie", true, Date(), Date()),
        TodoItem("Run Marathon", false, Date(), null),
        TodoItem("Code Project", true, Date(), Date()),
        TodoItem("Yoga Session", false, Date(), null)
    ))
    val todos =_todos.asStateFlow()

    fun addTodo(item: TodoItem) {
        _todos.value.add(item)
    }

    fun getTodos(): StateFlow<MutableList<TodoItem>> {
        return todos
    }
}