package com.example.testentries2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testentries2.R
import com.example.testentries2.databinding.ActivityMainBinding
import com.example.testentries2.model.Note
import com.example.testentries2.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteListAdapter
    private var allNotes: List<Note> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NoteListAdapter { note -> adapterOnClick(note) }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        noteViewModel.allNotes.observe(this) { notes ->
            Log.d("All notes",notes.toString())
            allNotes = notes
            adapter.submitList(allNotes)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchNotes(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchNotes(it)
                }
                return true
            }
        })

        binding.fab.setOnClickListener {
            val intent = Intent(this, NewNoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun searchNotes(query: String) {
        val filteredNotes = allNotes.filter { note ->
            note.title.contains(query, true) || note.content.contains(query, true)
        }
        adapter.submitList(filteredNotes)
    }

    private fun adapterOnClick(note: Note) {
        val intent = Intent(this, EditNoteActivity::class.java)
        intent.putExtra(EditNoteActivity.NOTE_ID, note.id)
        startActivity(intent)
    }
}