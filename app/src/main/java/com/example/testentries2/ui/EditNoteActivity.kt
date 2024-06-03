package com.example.testentries2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.testentries2.R
import com.example.testentries2.databinding.ActivityEditNoteBinding
import com.example.testentries2.model.Note
import com.example.testentries2.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditNoteActivity : AppCompatActivity() {

    companion object {
        const val NOTE_ID = "note_id"
    }

    private lateinit var binding: ActivityEditNoteBinding
    private val noteViewModel: NoteViewModel by viewModels()
    private var noteId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteId = intent.getIntExtra(NOTE_ID, 0)

        noteViewModel.allNotes.observe(this) { notes ->
            notes.find { it.id == noteId }?.let { note ->
                binding.noteTitle.setText(note.title)
                binding.noteContent.setText(note.content)
                binding.notePriority.setText(note.priority.toString())
            }
        }

        binding.saveButton.setOnClickListener {
            val title = binding.noteTitle.text.toString()
            val content = binding.noteContent.text.toString()
            val priority = binding.notePriority.text.toString().toIntOrNull() ?: 0

            if (title.isNotEmpty() && content.isNotEmpty()) {
                val note = Note(
                    id = noteId,
                    title = title,
                    content = content,
                    priority = priority
                )
                noteViewModel.insert(note)
                finish()
            } else {
                Toast.makeText(this, "Title or content cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.deleteButton.setOnClickListener {
            noteViewModel.allNotes.value?.find { it.id == noteId }?.let { note ->
                noteViewModel.delete(note)
                finish()
            }
        }
    }
}