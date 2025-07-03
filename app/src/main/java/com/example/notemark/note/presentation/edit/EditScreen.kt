package com.example.notemark.note.presentation.edit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notemark.R
import com.example.notemark.core.domain.util.DataError
import com.example.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import com.example.notemark.core.presentation.util.ObserveAsEvent
import com.example.notemark.core.presentation.util.SnackBarController
import com.example.notemark.core.presentation.util.toString
import com.example.notemark.note.presentation.components.NoteConfirmationDialog
import com.example.notemark.note.presentation.edit.components.NoteEditTextField
import com.example.notemark.note.presentation.edit.components.NoteEditTopAppBar
import com.example.notemark.note.presentation.preview.PreviewModels
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditScreenRoot(
    isPortrait: Boolean,
    isTablet: Boolean,
    onBack: () -> Unit
) {
    val viewModel: EditViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.events) { event ->
        when (event) {
            EditEvent.OnSave,
            EditEvent.OnClose -> onBack()
        }
    }

    state.note?.let { _ ->
        EditScreen(
            isPortrait = isPortrait,
            isTablet = isTablet,
            title = state.title,
            content = state.content,
            showDialog = state.showDialog,
            onAction = viewModel::onAction
        )
    }

}

@Composable
fun EditScreen(
    isPortrait: Boolean,
    isTablet: Boolean,
    title: TextFieldValue,
    content: TextFieldValue,
    showDialog: Boolean,
    onAction: (EditAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }


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

    LaunchedEffect(Unit) {
        if (!isPortrait) {
            focusRequester.requestFocus()
            onAction(EditAction.OnChangeTitle(title.copy(
                selection = TextRange(title.text.length)
            )))
        }
    }

    BackHandler {
        onAction(EditAction.OnCloseClicked)
    }

    Scaffold(
        topBar = {
            NoteEditTopAppBar(
                onCloseClick = { onAction(EditAction.OnCloseClicked) },
                onSaveClick = { onAction(EditAction.OnSaveClicked) },
                modifier = Modifier.padding(end = 16.dp)
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        contentWindowInsets = WindowInsets.systemBars,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(innerPadding)
                .then(
                    if (!isPortrait)
                        Modifier
                            .zIndex(2f)
                    else
                        Modifier
                            .padding(innerPadding)
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .then(
                        if (!isPortrait)
                            Modifier.widthIn(max = 450.dp)
                        else
                            Modifier.fillMaxWidth()
                    )
                    .padding(16.dp)

                    .align(Alignment.TopCenter)
                    .imePadding()
                    .verticalScroll(rememberScrollState())
            ) {
                NoteEditTextField(
                    value = title,
                    onValueChange = { onAction(EditAction.OnChangeTitle(it)) },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    placeHolder = {
                        Text(
                            text = stringResource(R.string.title),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.surface
                        )
                    }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                NoteEditTextField(
                    value = content,
                    onValueChange = { onAction(EditAction.OnChangeContent(it)) },
                    singleLine = false,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp)
                )
            }
        }
    }

    if (showDialog)
        NoteConfirmationDialog(
            title = stringResource(R.string.discard_changes),
            text = stringResource(R.string.are_you_sure_you_want_to_discard_changes),
            cancelButtonText = stringResource(R.string.keep_editing),
            confirmButtonText = stringResource(R.string.discard),
            onDismiss = { onAction(EditAction.OnConfirmCancelClicked) },
            onConfirm = { onAction(EditAction.OnConfirmDiscardClicked) }
        )
}

@Composable
@Preview(showBackground = true)
fun EditScreenPreview() {
    NoteMarkTheme {
        EditScreen(
            isPortrait = true,
            isTablet = false,
            title = TextFieldValue(PreviewModels.note.title),
            content = TextFieldValue(PreviewModels.note.content),
            showDialog = false,
            onAction = {},
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 900, heightDp = 500)
fun EditScreenPreviewLandscape() {
    NoteMarkTheme {
        EditScreen(
            isPortrait = false,
            isTablet = false,
            title = TextFieldValue(PreviewModels.note.title),
            content = TextFieldValue(PreviewModels.note.content),
            showDialog = false,
            onAction = {}
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 1000, heightDp = 1500)
fun EditScreenPreviewTablet() {
    NoteMarkTheme {
        EditScreen(
            isPortrait = true,
            isTablet = true,
            title = TextFieldValue(PreviewModels.note.title),
            content = TextFieldValue(PreviewModels.note.content),
            showDialog = false,
            onAction = {}
        )
    }
}