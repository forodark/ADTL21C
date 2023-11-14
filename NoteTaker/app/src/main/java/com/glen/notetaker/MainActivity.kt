package com.glen.notetaker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NotesSingleton.notes.addAll(listOf(
            Note("Note 1", "Content of note 1"),
            Note("Note 2", "Content of note 2")
        ))

        recyclerView = findViewById(R.id.recyclerView)
        adapter = NoteAdapter(NotesSingleton.notes, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            // Open NoteActivity for creating a new note
            startActivity(Intent(this, NoteActivity::class.java))
        }
    }

    override fun onItemClick(position: Int) {
        // Open NoteActivity for reading/editing the selected note
        val intent = Intent(this, NoteActivity::class.java)
        intent.putExtra(NoteActivity.NOTE_INDEX_EXTRA, position)
        startActivity(intent)
    }
}


