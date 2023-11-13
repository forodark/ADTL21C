package com.glen.midtermexam2

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun getTodos(): List<Todo> {
        return todos
    }

    fun setTodos(todoList: MutableList<Todo>) {
        todos.clear()
        todos.addAll(todoList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos() {
        todos.removeAll { todo ->
            todo.isDone
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvTitle: TextView, cbDone: CheckBox) {
        if (cbDone.isChecked) {
            tvTitle.paintFlags = tvTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTitle.paintFlags = tvTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val current = todos[position]
        holder.itemView.apply {
            val tvTitle = findViewById<TextView>(R.id.tv_title)
            tvTitle.text = current.title

            val cbDone = findViewById<CheckBox>(R.id.cb_done)
            cbDone.isChecked = current.isDone

            toggleStrikeThrough(tvTitle, cbDone)
            cbDone.setOnCheckedChangeListener() { _, isChecked ->
                toggleStrikeThrough(tvTitle, cbDone)
                current.isDone = !current.isDone
            }

        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}