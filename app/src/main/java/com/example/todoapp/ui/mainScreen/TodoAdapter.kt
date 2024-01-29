package com.example.todoapp.ui.mainScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.TodoViewModel
import com.example.todoapp.data.TodoItem

class TodoAdapter(private val context: Context, val navController: NavController, val todoViewModel: TodoViewModel) :
    ListAdapter<TodoItem, TodoAdapter.TodoViewHolder>(TodoItemDiffCallback()) {

    class TodoItemDiffCallback : DiffUtil.ItemCallback<TodoItem>() {
        override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
            return oldItem == newItem
        }
    }

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val isDone = itemView.findViewById<ImageView>(R.id.isDoneImage)
        private val todoText = itemView.findViewById<TextView>(R.id.todoTextView)
        private val button = itemView.findViewById<ImageView>(R.id.btnInfo)

        fun onBind(todoItem: TodoItem) {
            if (todoItem.isDone) {
                isDone.setImageResource(R.drawable.done)
            } else {
                if (todoItem.importance) {
                    isDone.setImageResource(R.drawable.not_important_done)
                } else {
                    isDone.setImageResource(R.drawable.not_done)
                }
            }
            todoText.text = todoItem.text
            button.setOnClickListener {
                navController.navigate(R.id.todoInfoFragment)
                todoViewModel.chosenTodoItem = todoItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TodoViewHolder(
            layoutInflater.inflate(
                R.layout.todo_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
