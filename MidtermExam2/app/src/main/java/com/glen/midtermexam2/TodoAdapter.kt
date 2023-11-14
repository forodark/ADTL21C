package com.glen.midtermexam2

import android.graphics.Paint
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private val todos: MutableList<Todo>,
    private val showCreateTaskDialog: (Todo?) -> Unit,
    private val todoCheckedChangeListener: TodoCheckedChangeListener
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

    fun replaceTodo(oldTodo: Todo, newTodo: Todo) {
        val index = todos.indexOf(oldTodo)
        if (index != -1) {
            todos[index] = newTodo
            notifyItemChanged(index)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_todo,
            parent,
            false
        )
        return TodoViewHolder(view)
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

    private fun setStrikeThrough(tvTitle: TextView, cbDone: CheckBox) {
        tvTitle.paintFlags = if (cbDone.isChecked) {
            tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val current = todos[position]
        holder.itemView.apply {
            val tvTitle = findViewById<TextView>(R.id.tv_title)
            val tvDueDate = findViewById<TextView>(R.id.tv_due_date)
            val cbDone = findViewById<CheckBox>(R.id.cb_done)

            tvTitle.text = current.title
            tvDueDate.text = current.dueDate // Set the due date text



            // Handle the strike-through logic
            tvTitle.paintFlags = if (current.isDone) {
                tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            // Set up the click listener for the text
            tvTitle.setOnClickListener {
                current.isDone = !current.isDone
                setStrikeThrough(tvTitle, cbDone)
                todoCheckedChangeListener.onTodoCheckedChanged(current)
                notifyItemChanged(holder.adapterPosition)
            }
            tvDueDate.setOnClickListener {
                current.isDone = !current.isDone
                setStrikeThrough(tvTitle, cbDone)
                todoCheckedChangeListener.onTodoCheckedChanged(current)
                notifyItemChanged(holder.adapterPosition)
            }

            // Set up the long click listener for editing
            tvTitle.setOnLongClickListener {
                showCreateTaskDialog(current)
                true
            }

            // Set up the check box listener
            cbDone.isChecked = current.isDone
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                current.isDone = isChecked

                // Notify the listener (ListActivity) about the change
                setStrikeThrough(tvTitle, cbDone)
                todoCheckedChangeListener.onTodoCheckedChanged(current)
            }
        }
    }




    override fun getItemCount(): Int {
        return todos.size
    }

    fun sortTodos() {
        todos.sortWith(compareBy({ it.dueDate.isEmpty() }, { it.dueDate }))
        notifyDataSetChanged()
    }


    interface TodoCheckedChangeListener {
        fun onTodoCheckedChanged(todo: Todo)
    }


}

