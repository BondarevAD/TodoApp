package com.example.todoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel  constructor(
    private val application: Application
) : AndroidViewModel(application) {
    val todoItemsRepository = (application as TodoApp).repository
    val todoItems: LiveData<MutableList<TodoItem>> = todoItemsRepository.allTodoItems.asLiveData()
    val isDoneShowing: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    var chosenTodoItem: TodoItem? = null
    val doneCount: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(0)
    }
    val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        coroutineScope.launch {
            updateState()
        }
    }

    fun addTodo(item: TodoItem) {
        coroutineScope.launch {
            todoItemsRepository.addTodo(item)
        }

    }

    fun deleteTodo(item: TodoItem) {
        coroutineScope.launch {
            item.id?.let { todoItemsRepository.deleteTodo(it) }
        }
    }

    fun updateTodo(newTodoItem: TodoItem) {
        coroutineScope.launch {
            todoItemsRepository.updateTodo(newTodoItem)
        }
    }

    fun toggleShowing() {
        isDoneShowing.value = !isDoneShowing.value!!
    }

     fun updateState(){
        if (todoItems.value != null) {
            doneCount.postValue(todoItems.value!!.filter { it.isDone }.size)
        }
    }
}




