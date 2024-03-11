package com.example.jetpackcomposehiltroominmvvm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcomposehiltroominmvvm.room.NoteModel
import com.example.jetpackcomposehiltroominmvvm.viewmodel.NoteScreen
import com.example.jetpackcomposehiltroominmvvm.viewmodel.NoteViewModel

    @Composable
    fun NoteApp() {
        val noteViewModel: NoteViewModel = viewModel()
        val notes by noteViewModel.allNotes.observeAsState(initial = emptyList())
        val currentScreen by noteViewModel.currentScreen.collectAsState()

        when (currentScreen) {
            is NoteScreen.NotesList -> {
                ListOfNote(
                    notes = notes,
                    onNoteDelete = { note -> noteViewModel.delete(note) },
                    onNoteClick = { noteId -> noteViewModel.navigateToDetail(noteId) },
                    onAddNoteClick = { noteViewModel.navigateToNoteEntry() }

                )
            }

            is NoteScreen.NoteDetail -> {
                val noteDetail = currentScreen as NoteScreen.NoteDetail
                DetailOfNote(
                    noteId = noteDetail.noteId,
                    onBack = { noteViewModel.navigateBackToList() }
                ) { noteId, newText -> noteViewModel.updateText(noteId, newText as String) }
            }

            is NoteScreen.NoteEntry -> {
                NewNote(
                    onSaveNote = { note -> noteViewModel.insert(note) },
                    onBack = { noteViewModel.navigateBackToList() }
                )
            }
        }
    }


@Composable
fun ListOfNote(
    notes: List<NoteModel>,
    onNoteDelete: (NoteModel) -> Unit,
    onNoteClick: (Long) -> Unit,
    onAddNoteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(notes) { note ->
                ItemOfNote(note = note, onNoteDelete = { onNoteDelete(note) }) {
                    onNoteClick(note.id)
                }
            }
        }
        IconButton(
            onClick = { onAddNoteClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .background(color = Color.Blue, shape = CircleShape),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White
            )
        }
    }
}



