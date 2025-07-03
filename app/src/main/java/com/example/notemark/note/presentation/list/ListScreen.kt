@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.notemark.note.presentation.list

import android.view.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notemark.R
import com.example.notemark.core.domain.util.DataError
import com.example.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import com.example.notemark.core.presentation.util.ObserveAsEvent
import com.example.notemark.core.presentation.util.SnackBarController
import com.example.notemark.core.presentation.util.toString
import com.example.notemark.note.domain.Note
import com.example.notemark.note.presentation.list.components.NoteCard
import com.example.notemark.note.presentation.components.NoteConfirmationDialog
import com.example.notemark.note.presentation.list.components.NoteEmptyList
import com.example.notemark.note.presentation.list.components.NoteFloatingActionButton
import com.example.notemark.note.presentation.list.components.NoteTopAppBar
import com.example.notemark.note.presentation.model.toNoteUi
import com.example.notemark.note.presentation.preview.PreviewModels
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.UUID

@Composable
fun ListScreenRoot(
    isPortrait: Boolean,
    isTablet: Boolean,
    onNoteClick: (String) -> Unit,
    onNotePosted: (newId: String) -> Unit,
    onSettingsClick: () -> Unit
) {
    val viewModel: ListViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.events) { event ->
        when (event) {
            is ListEvent.NoteSuccessfullyPosted -> onNotePosted(event.note.id)
        }
    }

    ListScreen(
        isPortrait = isPortrait,
        isTablet = isTablet,
        isLoading = state.isLoading,
        userInitials = state.userInitials,
        noteList = state.notes,
        showConfirmationDialog = state.showConfirmationDialog,
        onNoteClick = onNoteClick,
        onAction = { action ->
            when (action) {
                is ListAction.OnSettingsClick -> onSettingsClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun ListScreen(
    isPortrait: Boolean,
    isTablet: Boolean,
    isLoading: Boolean,
    userInitials: String,
    noteList: List<Note>,
    showConfirmationDialog: Boolean,
    onNoteClick: (String) -> Unit,
    onAction: (ListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    ObserveAsEvent(SnackBarController.events, snackbarHostState) { event ->
        scope.launch {
            when (event.message) {
                is DataError -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.toString(context),
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            NoteTopAppBar(
                userInitials = userInitials,
                onSettingsClick = { onAction(ListAction.OnSettingsClick) }
            )
        },
        floatingActionButton = {
            NoteFloatingActionButton(
                onClick = { onAction(ListAction.OnFabClick) },
                modifier = Modifier.safeContentPadding()
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        contentWindowInsets = WindowInsets.statusBars,
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .then(
                    if (!isPortrait)
                        Modifier.windowInsetsPadding(WindowInsets.displayCutout)
                    else
                        Modifier
                )
                .padding(innerPadding)
        ) {
            if (noteList.isEmpty())
                NoteEmptyList()
            else
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(if (isPortrait) 2 else 3),
                    verticalItemSpacing = 16.dp,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(items = noteList, key = { it.id }) { note ->
                        NoteCard(
                            note = note.toNoteUi(),
                            isTablet = isTablet,
                            modifier = Modifier
                                .combinedClickable(
                                    onClick = { onNoteClick(note.id) },
                                    onLongClick = { onAction(ListAction.OnNoteLongClick(note)) }
                                )
                        )
                    }
                }

            if (isLoading)
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
        }
    }

    if (showConfirmationDialog)
        NoteConfirmationDialog(
            title = stringResource(R.string.delete_note),
            text = stringResource(R.string.delete_note_description),
            cancelButtonText = stringResource(R.string.cancel),
            confirmButtonText = stringResource(R.string.delete),
            onDismiss = { onAction(ListAction.OnDialogCancel) },
            onConfirm = { onAction(ListAction.OnDeleteClick) },
        )
}

@Composable
@Preview(showBackground = true)
fun NoteListScreenPreview() {
    NoteMarkTheme {
        val notes = remember {
            (1..20).map {
                PreviewModels.note.copy(
                    id = UUID.randomUUID().toString(),
                )
            }
        }

        ListScreen(
            isPortrait = true,
            isTablet = false,
            isLoading = true,
            userInitials = "MF",
            noteList = notes,
            showConfirmationDialog = false,
            onNoteClick = {},
            onAction = {}
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 900, heightDp = 500)
fun NoteListScreenPreviewLandscape() {
    NoteMarkTheme {
        val notes = remember {
            (1..20).map {
                PreviewModels.note.copy(
                    id = UUID.randomUUID().toString(),
                )
            }
        }

        ListScreen(
            isPortrait = false,
            isTablet = false,
            isLoading = false,
            userInitials = "MF",
            noteList = notes,
            showConfirmationDialog = false,
            onNoteClick = {},
            onAction = {}
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 1000, heightDp = 1500)
fun NoteListScreenPreviewTablet() {
    NoteMarkTheme {
        val notes = remember {
            (1..20).map {
                PreviewModels.note.copy(
                    id = UUID.randomUUID().toString(),
                )
            }
        }

        ListScreen(
            isPortrait = true,
            isTablet = true,
            isLoading = false,
            userInitials = "MF",
            noteList = notes,
            showConfirmationDialog = false,
            onNoteClick = {},
            onAction = {},
        )
    }
}

