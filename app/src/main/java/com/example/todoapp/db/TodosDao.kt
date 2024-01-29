package com.example.todoapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.TodoItem
import kotlinx.coroutines.flow.Flow


@Dao
interface TodosDao {
    @Query("SELECT * FROM todos")
     fun getAllTodos(): Flow<MutableList<TodoItem>>

    @Query("DELETE FROM todos WHERE id LIKE :id")
    fun deleteTodoByID(id: Long): Int

    @Insert
    fun addTodo(vararg todoItem: TodoItem): List<Long>

    @Update
    fun updateTodo(todo: TodoItem): Int
}


