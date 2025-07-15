package com.example.hnotes.core.design.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.hnotes.core.design.icon.AppIcons
import com.example.hnotes.core.design.theme.AppTheme

@Composable
fun AppBottomAppBar(
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    floatingActionButton: (@Composable () -> Unit)? = null,
    containerColor: Color = BottomAppBarDefaults.containerColor,
    contentColor: Color = contentColorFor(containerColor),
    contentPadding: PaddingValues = BottomAppBarDefaults.ContentPadding,
) {
    BottomAppBar(
        actions = actions,
        modifier = modifier,
        floatingActionButton = floatingActionButton,
        containerColor = containerColor,
        contentColor = contentColor,
        contentPadding = contentPadding
    )
}

@ThemePreviews
@Composable
private fun AppBottomAppBarPreview() {
    AppTheme {
        AppBottomAppBar(
            actions = {
                AppIconButton(
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            imageVector = AppIcons.Checked,
                            contentDescription = "Action 1",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                )

                AppIconButton(
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            imageVector = AppIcons.Reminder,
                            contentDescription = "Action 1",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                )
                AppIconButton(
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            imageVector = AppIcons.PinBorder,
                            contentDescription = "Action 3",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                )
                AppIconButton(
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            imageVector = AppIcons.Copy,
                            contentDescription = "Action 3",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                )

                AppIconButton(
                    onClick = { /*TODO*/ }, icon = {
                        Icon(
                            imageVector = AppIcons.Delete,
                            contentDescription = "Action 3",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                )
            },
            modifier = Modifier
        )
    }
}