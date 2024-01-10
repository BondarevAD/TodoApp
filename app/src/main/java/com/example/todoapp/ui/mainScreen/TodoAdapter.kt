package com.example.todoapp.ui.mainScreen

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.TodoItem

class TodoAdapter(private val context: Context, private val dataset: MutableList<TodoItem>):
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val isDone = itemView.findViewById<ImageView>(R.id.isDoneImage)
        private val todoText = itemView.findViewById<TextView>(R.id.todoTextView)
        private val button = itemView.findViewById<ImageView>(R.id.btnInfo)

        fun onBind(todoItem: TodoItem) {
            if(todoItem.isDone) {
                isDone.setImageResource(R.drawable.done)
            }
            else {
                if(todoItem.priority) {
                    isDone.setImageResource(R.drawable.not_important_done)
                } else {
                    isDone.setImageResource(R.drawable.not_done)
                }
            }
            todoText.text = todoItem.id
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

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.onBind(dataset[position])
    }

}