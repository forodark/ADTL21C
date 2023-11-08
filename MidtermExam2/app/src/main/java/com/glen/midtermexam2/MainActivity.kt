package com.glen.midtermexam2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : BaseActivity() {

    private lateinit var todoAdapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoAdapter = TodoAdapter(mutableListOf())

        val rvTodo = findViewById<RecyclerView>(R.id.rv_todo)
        rvTodo.adapter = todoAdapter

        rvTodo.layoutManager = LinearLayoutManager(this)

        val btAdd = findViewById<Button>(R.id.bt_add)
        btAdd.setOnClickListener {
            val todoTitle = findViewById<EditText>(R.id.et_todo).text.toString()
            if(todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle)
                todoAdapter.addTodo(todo)
                findViewById<EditText>(R.id.et_todo).text.clear()
            }
        }

        val btClear = findViewById<Button>(R.id.bt_clear)
        btClear.setOnClickListener {
            todoAdapter.deleteDoneTodos()
        }
    }
}