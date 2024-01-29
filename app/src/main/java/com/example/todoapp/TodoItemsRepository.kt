package com.example.todoapp

import androidx.annotation.WorkerThread
import com.example.todoapp.data.TodoItem
import com.example.todoapp.db.TodosDao
import javax.inject.Inject

class TodoItemsRepository constructor(private val todosDao: TodosDao) {

    val allTodoItems = todosDao.getAllTodos()

    @WorkerThread
    suspend fun addTodo(todo: TodoItem) {
        todosDao.addTodo(todo)
    }
    @WorkerThread
     suspend fun deleteTodo(id: Long) {
        todosDao.deleteTodoByID(id)
    }

    @WorkerThread
     suspend fun updateTodo(todo: TodoItem): Int {
        return todosDao.updateTodo(todo)
    }

}