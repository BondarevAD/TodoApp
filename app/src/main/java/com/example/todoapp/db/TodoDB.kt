package com.example.todoapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todoapp.data.TodoItem
import com.example.todoapp.utils.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [TodoItem::class], version = 1)
@TypeConverters(Converters::class)
abstract class TodoDB: RoomDatabase() {
    abstract fun todosDao(): TodosDao

    companion object {
        @Volatile
        private var INSTANCE: TodoDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): TodoDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDB::class.java,
                    "food_item_database"
                ).addCallback(TodoItemCallback(scope))
                    .build()

                INSTANCE = instance

                // return instance
                instance
            }
        }
    }

    private class TodoItemCallback(val scope: CoroutineScope):RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let { foodItemRoomDB ->
                scope.launch {
                    // if you want to populate database
                    // when RoomDatabase is created
                    // populate here
                }
            }
        }
    }
}