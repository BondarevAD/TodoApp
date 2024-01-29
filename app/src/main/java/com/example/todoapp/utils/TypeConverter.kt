package com.example.todoapp.utils

import androidx.room.TypeConverter
import com.example.todoapp.data.TodoItem
import com.google.gson.Gson
import java.time.LocalDate
import java.time.LocalDateTime

class Converters {

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDate? {
        if(value != "null") {
            return LocalDate.parse(value)
        }
        return null
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date.toString()
    }

    @TypeConverter
    fun fromTodo(todo: TodoItem): String {
        // Convert the Product object to a JSON string or any other format suitable for storage
        // For example, you can use Gson library to convert the object to JSON
        return Gson().toJson(todo)
    }

    @TypeConverter
    fun toProduct(json: String): TodoItem {
        // Convert the stored string back to a Product object
        // For example, you can use Gson library to convert the JSON string to a Product object
        return Gson().fromJson(json, TodoItem::class.java)
    }

}