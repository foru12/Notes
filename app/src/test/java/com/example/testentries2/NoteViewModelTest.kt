package com.example.testentries2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testentries2.model.Note
import com.example.testentries2.repository.NoteRepository
import com.example.testentries2.viewmodel.NoteViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NoteViewModelTest {

    // Создаем фиктивные объекты для репозитория и корутины
    @Mock
    lateinit var repository: NoteRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: NoteViewModel

    @Before
    fun setup() {
        // Инициализируем ViewModel с фиктивным репозиторием
        viewModel = NoteViewModel(repository)
    }

    @Test
    suspend fun `test insert note`() {
        // Создаем заметку для вставки
        val note = Note(
            id = 1,
            title = "Test Note",
            content = "This is a test note",
            priority = 1
        )

        // Запускаем метод вставки
        viewModel.insert(note)

        // Проверяем, что метод insert был вызван у репозитория с правильными аргументами
        verify(repository).insert(note)
    }
}