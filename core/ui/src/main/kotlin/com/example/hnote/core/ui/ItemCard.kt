package com.example.hnote.core.ui

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextDecoration
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
    val transition = updateTransition(
        targetState = item.checked,
        label = "Check Transition"
    )
    val alpha by transition.animateFloat(label = "Alpha") {
        if (it) 0.5f else 1f
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp, alignment = Alignment.Start)
    ) {
        Icon(
            modifier = Modifier
                .size(size = 16.dp)
                .alpha(alpha = alpha),
            imageVector = if (item.checked) AppIcons.Checked else AppIcons.Unchecked,
            contentDescription = null
        )

        Text(
            modifier = Modifier.alpha(alpha = alpha),
            text = item.content,
            style = MaterialTheme.typography.bodyLarge,
            textDecoration = if (item.checked) TextDecoration.LineThrough else null
        )
    }
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
