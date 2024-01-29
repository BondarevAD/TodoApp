package com.example.todoapp.ui.mainScreen

import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.TodoViewModel
import com.example.todoapp.data.TodoItem
import com.example.todoapp.databinding.FragmentMainScreenBinding
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class MainScreenFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    lateinit var navController: NavController
    private val todoViewModel: TodoViewModel by activityViewModels()


      private var _binding: FragmentMainScreenBinding? = null
          private val binding get() = _binding!!
          
          override fun onCreateView(
              inflater: LayoutInflater, container: ViewGroup?,
              savedInstanceState: Bundle?
          ): View? {
              _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
              val view = binding.root
              return view
          }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdd.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white))
        var todos: MutableList<TodoItem> = todoViewModel.todoItems.value?.filter { !it.isDone }?.toMutableList() ?: mutableListOf()

        todoViewModel.doneCount.observe(viewLifecycleOwner) { doneCount ->
            binding.doneTextView.text = "Выполнено: ${doneCount}"
        }

        binding.btnShowDone.setOnClickListener {
            todoViewModel.toggleShowing()
        }
        navController = Navigation.findNavController(view)
        recyclerView = binding.recyclerView

        val todoAdapter = TodoAdapter(requireContext(), navController, todoViewModel)
        todoAdapter.submitList(todos)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = todoAdapter
        recyclerView.layoutManager = layoutManager

        binding.btnAdd.setOnClickListener {
            navController.navigate(R.id.todoInfoFragment)
        }

        val callbackMethod =
            object :
                SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    when (direction) {
                        ItemTouchHelper.LEFT -> {
                            var todoFromViewModel = todoViewModel
                                .todoItems.value!!
                                .get(position)
                            if(todoViewModel.isDoneShowing.value == false) {
                                todoFromViewModel = todoViewModel.todoItems.value!!.filter { !it.isDone }
                                    .toMutableList().get(position)
                            }
                            todoViewModel.deleteTodo(todoFromViewModel)
                            if (todoViewModel.isDoneShowing.value == true) {
                                recyclerView.adapter?.notifyItemRemoved(position)
                            } else {
                                val newTodos = todoViewModel.todoItems.value!!.filter { !it.isDone }
                                todoAdapter.submitList(newTodos)
                            }
                        }

                        ItemTouchHelper.RIGHT -> {
                            var todoFromViewModel = todoViewModel
                                .todoItems.value!!
                                .get(position)
                            if(todoViewModel.isDoneShowing.value == false) {
                                todoFromViewModel = todoViewModel.todoItems.value!!.filter { !it.isDone }
                                    .toMutableList().get(position)
                            }
                            val newTodo = TodoItem(
                                id = todoFromViewModel.id,
                                text = todoFromViewModel.text,
                                isDone = !todoFromViewModel.isDone,
                                creationDate = todoFromViewModel.creationDate,
                                changeDate = todoFromViewModel.changeDate,
                                deadline = todoFromViewModel.deadline,
                                importance = todoFromViewModel.importance
                            )
                            todoViewModel.updateTodo(newTodo)
                            if (todoViewModel.isDoneShowing.value == true) {
                                val newTodos = todoViewModel.todoItems.value
                                todoAdapter.submitList(newTodos)
                            } else {
                                val newTodos = todoViewModel.todoItems.value!!.filter { !it.isDone }
                                todoAdapter.submitList(newTodos)
                            }
                        }
                    }
                }


                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    RecyclerViewSwipeDecorator.Builder(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                        .addSwipeLeftActionIcon(R.drawable.baseline_delete_white_24)
                        .addSwipeLeftBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red
                            )
                        )
                        .addSwipeRightActionIcon(R.drawable.baseline_check_24)
                        .addSwipeRightBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.green
                            )
                        )
                        .create()
                        .decorate()


                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }

            }



        val itemTouchHelper = ItemTouchHelper(callbackMethod)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        todoViewModel.isDoneShowing.observe(requireActivity()) {
            if (!todoViewModel.isDoneShowing.value!!) {
                binding.btnShowDone.setImageResource(R.drawable.baseline_visibility_24)
                val newTodos = todoViewModel.todoItems.value?.filter { !it.isDone }?.toMutableList() ?: mutableListOf()
                todoAdapter.submitList(newTodos)
            } else {
                binding.btnShowDone.setImageResource(R.drawable.baseline_visibility_off_24)
                val newTodos = todoViewModel.todoItems.value!!
                todoAdapter.submitList(newTodos)
            }
        }

        todoViewModel.todoItems.observe(requireActivity()) { todoItems ->
            if(todoViewModel.isDoneShowing.value == false) {
                val filteredTodos = todoItems.filter { !it.isDone }
                todoAdapter.submitList(filteredTodos.toMutableList())
            }
            else {
                todoAdapter.submitList(todoItems)
            }
            todoViewModel.updateState()
        }


    }
}
