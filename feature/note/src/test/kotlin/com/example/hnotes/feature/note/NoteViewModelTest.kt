package com.example.hnotes.feature.note

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class NoteViewModelTest {
    private val navigator = TestNavigator()
    private val noteRepository = TestNoteRepository()
    private val savedStateHandle = SavedStateHandle()

    private lateinit var viewModel: NoteViewModel

    @Before
    fun setup() {
        savedStateHandle["noteId"] = null
        viewModel = NoteViewModel(
            navigator = navigator,
            noteRepository = noteRepository,
            savedStateHandle = savedStateHandle
        )
    }

    @Test
    fun `view model starts with default state`() = runTest {
        savedStateHandle["noteId"] = 1L
        val initialState = viewModel.uiState.value
        assertEquals(NoteUiState().note?.id, initialState.note?.id)
        assertEquals(NoteUiState().title, initialState.title)
        assertEquals(NoteUiState().content, initialState.content)
        assertEquals(NoteUiState().backgroundColor, initialState.backgroundColor)
    }
}