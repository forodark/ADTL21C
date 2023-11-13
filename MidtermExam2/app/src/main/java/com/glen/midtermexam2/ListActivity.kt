package com.glen.midtermexam2

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.SharedPreferences

class ListActivity : BaseActivity() {

    private lateinit var todoAdapter: TodoAdapter
//    private lateinit var prefs: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoAdapter = TodoAdapter(mutableListOf())

        PreferencesUtil.getInstance(this);

        supportActionBar?.title = "Task Lister"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val rvTodo = findViewById<RecyclerView>(R.id.rv_todo)
        rvTodo.adapter = todoAdapter

        rvTodo.layoutManager = LinearLayoutManager(this)

        loadTodos()

        val btAdd = findViewById<Button>(R.id.bt_add)
        btAdd.setOnClickListener {
            val todoTitle = findViewById<EditText>(R.id.et_todo).text.toString()
            if(todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle)
                todoAdapter.addTodo(todo)
                findViewById<EditText>(R.id.et_todo).text.clear()

                saveTodos()
            }
        }

        val btClear = findViewById<Button>(R.id.bt_clear)
        btClear.setOnClickListener {
            todoAdapter.deleteDoneTodos()

            saveTodos()
        }
    }

    private fun saveTodos() {
        val editor = PreferencesUtil.getEditor()
        val todos = todoAdapter.getTodos()
        val todoStrings = todos.map { it.title }
        editor.putStringSet("todos", todoStrings.toSet())
        editor.apply()
    }

    private fun loadTodos() {
        val todosSet = PreferencesUtil.getPrefs().getStringSet("todos", setOf())
        val todos = todosSet?.map { Todo(it) } ?: emptyList()
        todoAdapter.setTodos(todos.toMutableList())
    }
}