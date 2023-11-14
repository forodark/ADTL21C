package com.glen.notetaker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class NoteActivity : AppCompatActivity() {


    private lateinit var editTitle: EditText
    private lateinit var editContent: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        editTitle = findViewById(R.id.editTitle)
        editContent = findViewById(R.id.editContent)
        btnSave = findViewById(R.id.btnSave)

        val noteIndex = intent.getIntExtra(NOTE_INDEX_EXTRA, -1)
        if (noteIndex != -1) {
            // Edit existing note
            val note = NotesSingleton.notes[noteIndex]
            editTitle.setText(note.title)
            editContent.setText(note.content)
        }

        btnSave.setOnClickListener {
            saveNote(noteIndex)
        }
    }

    private fun saveNote(noteIndex: Int) {
        val title = editTitle.text.toString()
        val content = editContent.text.toString()

        if (title.isNotEmpty() || content.isNotEmpty()) {
            if (noteIndex != -1) {
                // Edit existing note
                val editedNote = Note(title, content)
                NotesSingleton.notes[noteIndex] = editedNote
            } else {
                // Create new note
                val newNote = Note(title, content)
                NotesSingleton.notes.add(newNote)
            }
            finish() // Close NoteActivity
        }
    }

    companion object {
        const val NOTE_INDEX_EXTRA = "noteIndex"
    }
}
