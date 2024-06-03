package com.example.testentries2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.testentries2.R
import com.example.testentries2.databinding.ActivityNewNoteBinding
import com.example.testentries2.model.Note
import com.example.testentries2.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewNoteBinding
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val title = binding.noteTitle.text.toString()
            val content = binding.noteContent.text.toString()
            val priority = binding.notePriority.text.toString().toIntOrNull() ?: 0

            if (title.isNotEmpty() && content.isNotEmpty()) {
                val note = Note(
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
    }
}
