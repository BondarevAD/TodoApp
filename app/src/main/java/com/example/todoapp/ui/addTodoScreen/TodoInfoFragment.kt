package com.example.todoapp.ui.addTodoScreen

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todoapp.R
import com.example.todoapp.TodoViewModel
import com.example.todoapp.data.TodoItem
import com.example.todoapp.databinding.FragmentTodoInfoBinding
import java.time.LocalDate
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class TodoInfoFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var _binding: FragmentTodoInfoBinding? = null
    private val binding get() = _binding!!
    lateinit var navController: NavController
    private val todoViewModel: TodoViewModel by activityViewModels()
    var isImportant: Boolean = false
    var todoDay = 0
    var todoMonth = 0
    var todoYear = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTodoInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(todoViewModel.chosenTodoItem == null) {
            binding.btnDelete.setTextColor(resources.getColor(R.color.labelDisable))
            binding.imageDelete.setImageResource(R.drawable.baseline_delete_24)
        }
        else {
            binding.btnDelete.setTextColor(resources.getColor(R.color.red))
            binding.imageDelete.setImageResource(R.drawable.baseline_delete_red_24)
            binding.btnDelete.setOnClickListener {
                todoViewModel.deleteTodo(todoViewModel.chosenTodoItem!!)
                navController.popBackStack()
            }
        }
        val thumbColors = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(
                ContextCompat.getColor(requireContext(), R.color.blue),
                ContextCompat.getColor(requireContext(), R.color.black)
            )
        )

        val trackColor = ContextCompat.getColor(requireContext(), R.color.blueLight)

        binding.switchDate.thumbTintList = thumbColors
        binding.switchDate.trackTintList = ColorStateList.valueOf(trackColor)


        val spinner = binding.importanceChoise
        spinner.onItemSelectedListener = this

        navController = Navigation.findNavController(view)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        todoViewModel.chosenTodoItem?.let {
            binding.editName.setText(it.text)

            isImportant = it.importance
            if(isImportant) {
                spinner.setSelection(1)
            }
            else {
                spinner.setSelection(0)
            }

            val date = it.deadline

            if(date != null) {
                binding.switchDate.isChecked = true

                val month = date.monthValue
                val day = date.dayOfMonth
                val year = date.year

                binding.todoDate.text = "${day}.${month}.${year}"
            }
        }

        binding.btnSave.setOnClickListener {
            val textTodo = binding.editName.text.toString()

            val newTodo: TodoItem

            var creationDate: LocalDate
            var id: Long?

            if(todoViewModel.chosenTodoItem == null) {
                creationDate = LocalDate.now()
                id = null

            }
            else {
                creationDate = todoViewModel.chosenTodoItem!!.creationDate
                id  = todoViewModel.chosenTodoItem!!.id!!
            }

            if (todoDay == 0 && todoMonth == 0 && todoYear == 0) {
                newTodo = TodoItem(
                    id = id,
                    text = textTodo,
                    isDone = false,
                    creationDate = creationDate,
                    changeDate = LocalDate.now(),
                    deadline = null,
                    importance = isImportant
                )
            }
            else {
                val dateToDo = LocalDate.of(todoYear, todoMonth, todoDay)

                newTodo = TodoItem(
                    id = id,
                    text = textTodo,
                    isDone = false,
                    creationDate = creationDate,
                    changeDate = LocalDate.now(),
                    deadline = dateToDo,
                    importance = isImportant
                )
            }

            if(todoViewModel.chosenTodoItem == null) {
                todoViewModel.addTodo(newTodo)
            }
            else {
                newTodo.isDone = todoViewModel.chosenTodoItem!!.isDone
                todoViewModel.updateTodo(newTodo)
                Log.d("!!newtodo", newTodo.toString())
            }
            todoViewModel.chosenTodoItem = null
            navController.popBackStack()
        }

        binding.switchDate.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val calendar = Calendar.getInstance()

                val mYear = calendar.get(Calendar.YEAR)
                val mMonth = calendar.get(Calendar.MONTH)
                val mDay = calendar.get(Calendar.DAY_OF_MONTH)

                DatePickerDialog(requireContext(), {_, year, month, day ->
                    todoDay = day
                    todoMonth = month + 1
                    todoYear = year
                    binding.todoDate.text = "${todoDay}.${todoMonth}.${todoYear}"

                }, mYear, mMonth, mDay).show()
            } else {
                binding.todoDate.text = ""
            }
        }
        binding.btnClose.setOnClickListener {
            todoViewModel.chosenTodoItem = null
            navController.popBackStack()
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0 != null) {
            when(p0.getItemAtPosition(p2).toString()) {
                "Нет" -> isImportant = false
                "!!Высокая" -> isImportant = true
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO()
    }

}