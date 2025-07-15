package com.example.hnotes.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.hnotes.core.design.component.AppIconButton
import com.example.hnotes.core.design.icon.AppIcons
import com.example.hnotes.core.model.SearchQuery

@Composable
fun SearchQueryCard(
    searchQuery: SearchQuery,
    onClick: (SearchQuery) -> Unit,
    onClear: (SearchQuery) -> Unit,
    modifier: Modifier = Modifier
) {

    ListItem(
        headlineContent = {
            Text(text = searchQuery.query)
        },
        leadingContent = {
            Icon(
                imageVector = AppIcons.History,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingContent = {
            AppIconButton(
                onClick = { onClear(searchQuery) },
                icon = {
                    Icon(
                        imageVector = AppIcons.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            )
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        modifier = modifier.clickable { onClick(searchQuery) }
    )
}