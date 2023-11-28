package com.glen.midtermexam2

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.icu.util.Calendar
import android.view.MenuItem
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ListActivity : BaseActivity(), TodoAdapter.TodoCheckedChangeListener {

    private lateinit var todoAdapter: TodoAdapter
//    private lateinit var prefs: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        PreferencesUtil.getInstance(this)

        supportActionBar?.title = "Task Lister"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val rvTodo = findViewById<RecyclerView>(R.id.rv_todo)

        todoAdapter = TodoAdapter(mutableListOf(), this::showCreateTaskDialog, this)


        rvTodo.adapter = todoAdapter

        rvTodo.layoutManager = LinearLayoutManager(this)

        loadTodos()
        todoAdapter.sortTodos()
//        val btAdd = findViewById<Button>(R.id.bt_add)
//        btAdd.setOnClickListener {
//            val todoTitle = findViewById<EditText>(R.id.et_todo).text.toString()
//            if(todoTitle.isNotEmpty()) {
//                val todo = Todo(todoTitle)
//                todoAdapter.addTodo(todo)
//                findViewById<EditText>(R.id.et_todo).text.clear()
//
//                saveTodos()
//            }
//        }

//        val btClear = findViewById<Button>(R.id.bt_clear)
//        btClear.setOnClickListener {
//            todoAdapter.deleteDoneTodos()
//
//            saveTodos()
//        }

        val fabAddTask = findViewById<FloatingActionButton>(R.id.fab_add_task)
        fabAddTask.setOnClickListener {
            showCreateTaskDialog()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.task_clear -> {
                todoAdapter.deleteDoneTodos()
                saveTodos()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showCreateTaskDialog(editingTask: Todo? = null) {
        val dialogView = layoutInflater.inflate(R.layout.create_task_dialog, null)
        val tvTaskTitle = dialogView.findViewById<TextView>(R.id.tv_task_title)
        val etTaskTitle = dialogView.findViewById<EditText>(R.id.et_task_title)
        val etDueDate = dialogView.findViewById<TextInputEditText>(R.id.et_due_date)
        val tilDueDate = dialogView.findViewById<TextInputLayout>(R.id.til_due_date)
        val btCreateTask = dialogView.findViewById<Button>(R.id.bt_create_task)

        if (editingTask != null) {
            tvTaskTitle.setText("Edit Task")
            btCreateTask.setText("Update")
            etTaskTitle.setText(editingTask.title)
            etDueDate.setText(editingTask.dueDate)
        }

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        etDueDate.setOnClickListener {
            showDatePickerDialog(etDueDate)
        }

        btCreateTask.setOnClickListener {
            val taskTitle = etTaskTitle.text.toString()
            val dueDate = etDueDate.text.toString()

            // Validate and create the task
            if (taskTitle.isNotEmpty()) {
                val todo = Todo(taskTitle, false, dueDate)
                if (editingTask != null) {
                    // Replace the existing task if editing
                    todoAdapter.replaceTodo(editingTask, todo)
                } else {
                    todoAdapter.addTodo(todo)
                }

                todoAdapter.sortTodos() // Sort the tasks
                saveTodos()
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun showDatePickerDialog(etDueDate: TextInputEditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                etDueDate.setText(formattedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
    private fun saveTodos() {
        val editor = PreferencesUtil.getEditor()
        val todos = todoAdapter.getTodos()
        val todoStrings = todos.map { "${it.title},${it.dueDate},${it.isDone}" }
        editor.putStringSet("todos", todoStrings.toSet())
        editor.apply()
    }


    private fun loadTodos() {
        val todosSet = PreferencesUtil.getPrefs().getStringSet("todos", setOf())
        val todos = todosSet?.map {
            val parts = it.split(",")
            if (parts.size == 3) {
                Todo(parts[0], dueDate = parts[1], isDone = parts[2].toBoolean())
            } else {
                Todo(it)
            }
        } ?: emptyList()
        todoAdapter.setTodos(todos.toMutableList())
    }


    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val clearMenuItem = menu.findItem(R.id.task_clear)

        if (clearMenuItem != null) {
            clearMenuItem.isVisible = true
        }

        // You may need to call invalidateOptionsMenu() if your changes don't reflect immediately.
        // invalidateOptionsMenu()

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onTodoCheckedChanged(todo: Todo) {
        // Handle the checkbox state change here
        saveTodos()
    }
}