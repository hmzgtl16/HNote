package com.example.hnote.core.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.hnote.core.design.component.AppBackground
import com.example.hnote.core.design.component.ThemePreviews
import com.example.hnote.core.design.icon.AppIcons
import com.example.hnote.core.design.theme.AppTheme
import com.example.hnote.core.model.Item
import com.example.hnote.core.model.Note

@Composable
fun ItemCard(
    item: Item,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
            Text(
                text = item.content,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        leadingContent = {
            Icon(
                imageVector = if (item.checked) AppIcons.Checked else AppIcons.Unchecked,
                contentDescription = null
            )
        },
        modifier = modifier
    )
}

@ThemePreviews
@Composable
private fun CheckedItemCardPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class)
    notes: Map<Boolean, List<Note>>
) {
    AppTheme {
        AppBackground(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 56.dp),
            content = {
                ItemCard(item = notes[true]!![6].items.first())
            }
        )
    }
}

@ThemePreviews
@Composable
private fun UncheckedItemCardPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class)
    notes: Map<Boolean, List<Note>>
) {
    AppTheme {
        AppBackground(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 56.dp),
            content = {
                ItemCard(item = notes[true]!![6].items.last())
            }
        )
    }
}
