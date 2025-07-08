package com.example.hnote.core.ui

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.hnote.core.design.component.AppIconToggleButton
import com.example.hnote.core.design.component.ThemePreviews
import com.example.hnote.core.design.icon.AppIcons
import com.example.hnote.core.design.theme.AppTheme
import com.example.hnote.core.design.theme.LocalTintTheme
import com.example.hnote.core.model.Note

@Composable
fun NoteCard(
    note: Note,
    multiSelectionEnabled: Boolean,
    enableMultiSelection: () -> Unit,
    selected: Boolean,
    onSelectedChanged: (Note) -> Unit,
    onNoteClick: (Long) -> Unit,
    onPinClick: (Note) -> Unit,
    modifier: Modifier = Modifier,
) {

    ElevatedCard(
        colors = if (note.backgroundColor != null)
            CardDefaults.elevatedCardColors(containerColor = Color(color = note.backgroundColor!!))
        else CardDefaults.elevatedCardColors(),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .combinedClickable(
                onClick = {
                    if (multiSelectionEnabled) onSelectedChanged(note)
                    else onNoteClick(note.id)
                },
                onLongClick = {
                    if (multiSelectionEnabled) return@combinedClickable
                    enableMultiSelection()
                    onSelectedChanged(note)
                },
                role = Role.RadioButton
            )
            .semantics { onClick(label = "Open Note", action = null) },
        content = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterVertically
                ),
                content = {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.Start
                        ),
                        content = {
                            Text(
                                text = note.title.ifEmpty(
                                    defaultValue = { stringResource(id = R.string.core_ui_note_untitled) }
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.weight(weight = 1f)
                            )

                            if (!multiSelectionEnabled) {

                                AppIconToggleButton(
                                    checked = note.pinned,
                                    onCheckedChange = { onPinClick(note) },
                                    icon = {
                                        Icon(
                                            imageVector = AppIcons.PinBorder,
                                            contentDescription = "Pinned"
                                        )
                                    },
                                    checkedIcon = {
                                        Icon(
                                            imageVector = AppIcons.Pin,
                                            contentDescription = "Unpinned"
                                        )
                                    }
                                )
                            }

                            if (multiSelectionEnabled) {

                                RadioButton(
                                    selected = selected,
                                    onClick = null,
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                        unselectedColor = LocalTintTheme.current.iconTint
                                    )
                                )
                            }
                        }
                    )

                    if (note.content.isNotEmpty()) {
                        Text(
                            text = note.content,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    note.reminder?.let {
                        ReminderCard(
                            reminder = it,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    if (note.items.isNotEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(state = rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(
                                space = 4.dp,
                                alignment = Alignment.Top
                            ),
                            content = {
                                note.items.forEach {
                                    ItemCard(
                                        item = it,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        )
                    }
                }
            )
        }
    )
}

@Composable
fun NoteCard(
    note: Note,
    onNoteClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {

    ElevatedCard(
        onClick = { onNoteClick(note.id) },
        colors = if (note.backgroundColor != null)
            CardDefaults.elevatedCardColors(containerColor = Color(color = note.backgroundColor!!))
        else CardDefaults.elevatedCardColors(),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .semantics { onClick(label = "Open Note", action = null) },
        content = {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.CenterVertically
                        ),
                        content = {

                            Text(
                                text = note.title.ifEmpty(
                                    defaultValue = { stringResource(id = R.string.core_ui_note_untitled) }
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.fillMaxWidth()
                            )

                            if (note.content.isNotEmpty()) {
                                Text(
                                    text = note.content,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    )
                }
            )
        }
    )
}

@ThemePreviews
@Composable
fun SelectedNoteCardWithSelectionPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class)
    notes: Map<Boolean, List<Note>>,
) {
    AppTheme {
        NoteCard(
            note = notes[true]!![6],
            multiSelectionEnabled = true,
            enableMultiSelection = {},
            selected = true,
            onSelectedChanged = {},
            onNoteClick = {},
            onPinClick = {}
        )
    }
}

@ThemePreviews
@Composable
fun NoteCardWithSelectionPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class)
    notes: Map<Boolean, List<Note>>,
) {
    AppTheme {
        NoteCard(
            note = notes[true]!![5],
            multiSelectionEnabled = true,
            enableMultiSelection = {},
            selected = false,
            onSelectedChanged = {},
            onNoteClick = {},
            onPinClick = {}
        )
    }
}

@ThemePreviews
@Composable
fun NoteCardWithoutSelectionPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class)
    notes: Map<Boolean, List<Note>>
) {
    AppTheme {
        NoteCard(
            note = notes[true]!![1],
            multiSelectionEnabled = false,
            enableMultiSelection = {},
            selected = false,
            onSelectedChanged = {},
            onNoteClick = {},
            onPinClick = {}
        )
    }
}

@ThemePreviews
@Composable
fun NoteCardPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class)
    notes: Map<Boolean, List<Note>>,
) {
    AppTheme {
        NoteCard(
            note = notes[true]!![1],
            onNoteClick = {}
        )
    }
}