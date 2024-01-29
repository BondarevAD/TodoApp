package com.example.todoapp

import android.app.Application
import com.example.todoapp.db.TodoDB
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TodoApp: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy {
        TodoDB.getDatabase(this, applicationScope)
    }
    val repository by lazy {
        TodoItemsRepository(database.todosDao())
    }
}