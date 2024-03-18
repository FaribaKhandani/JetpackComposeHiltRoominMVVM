package com.example.jetpackcomposehiltroominmvvm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcomposehiltroominmvvm.room.NoteModel
import com.example.jetpackcomposehiltroominmvvm.viewmodel.NoteScreen
import com.example.jetpackcomposehiltroominmvvm.viewmodel.NoteViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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


@Composable
fun ItemOfNote(note: NoteModel, onNoteDelete: () -> Unit, onNoteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { onNoteClick.invoke() },

        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = note.text, modifier = Modifier.weight(1f), color = Color.Black)
            IconButton(
                onClick = {
                    onNoteDelete.invoke()
                },
                modifier = Modifier
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Blue
                )
            }
        }

        val dateformat = dateFormat(note.timestamp)
        Text(text = "Date: $dateformat", color = Color.Gray, modifier = Modifier.padding(5.dp))
    }
}

@Composable
fun DetailOfNote(
    noteId: Long,
    onBack: () -> Unit,
    onEdit: (Long, Any?) -> Unit
) {
    val noteViewModel: NoteViewModel = viewModel()
    val note by noteViewModel.getNoteById(noteId).collectAsState(initial = null)

    var editedText by remember { mutableStateOf("") }

    LaunchedEffect(note) {
        note?.let {
            editedText = it.text
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        )

        {


            IconButton(onClick = onBack, modifier = Modifier.size(48.dp)) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Blue
                )
            }

            IconButton(onClick = {
                onEdit(noteId, editedText)
            }) {
                Text("Save", color = Color.Blue)
            }
        }

        TextField(
            value = editedText,
            onValueChange = {
                editedText = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp))


    }
}


private fun dateFormat(time: Long): String {
    val formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val localDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault())
    return formatDate.format(localDate)
}



