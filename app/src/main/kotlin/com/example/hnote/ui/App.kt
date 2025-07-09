package com.example.hnote.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.hnote.R
import com.example.hnote.core.design.component.AppBackground
import com.example.hnote.core.design.component.AppGradientBackground
import com.example.hnote.core.design.component.AppIconButton
import com.example.hnote.core.design.component.AppTopAppBar
import com.example.hnote.core.design.icon.AppIcons
import com.example.hnote.core.navigation.NavigationEvent
import com.example.hnote.navigation.AppNavHost

@Composable
fun App(
    appState: AppState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    AppBackground(
        modifier = modifier,
        content = {
            AppGradientBackground {
                val snackbarHostState = remember { SnackbarHostState() }

                App(
                    appState = appState,
                    snackbarHostState = snackbarHostState,
                    windowAdaptiveInfo = windowAdaptiveInfo
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    appState: AppState,
    snackbarHostState: SnackbarHostState,
    windowAdaptiveInfo: WindowAdaptiveInfo,
    modifier: Modifier = Modifier,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = lifecycleOwner.lifecycle) {
        lifecycleOwner
            .repeatOnLifecycle(
                state = Lifecycle.State.STARTED,
                block = {
                    appState.navigator.events.collect {
                        when (it) {
                            is NavigationEvent.NavigateTo -> {
                                appState.navController.navigate(
                                    route = it.route,
                                    navOptions = it.navOptions
                                )
                            }

                            is NavigationEvent.NavigateBack -> {
                                appState.navController.navigateUp()
                            }
                        }
                    }
                }
            )
    }

    val shouldShowTopAppBar = appState.isMainDestination

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding)
                    .consumeWindowInsets(paddingValues = padding)
                    .windowInsetsPadding(
                        insets = WindowInsets.safeDrawing.only(sides = WindowInsetsSides.Horizontal)
                    ),
                content = {
                    AnimatedVisibility(
                        visible = shouldShowTopAppBar,
                        enter = slideInVertically(
                            initialOffsetY = { -it },
                            animationSpec = tween(
                                durationMillis = 150,
                                easing = LinearOutSlowInEasing
                            )
                        ),
                        exit = slideOutVertically(
                            targetOffsetY = { -it },
                            animationSpec = tween(
                                durationMillis = 250,
                                easing = FastOutLinearInEasing
                            )
                        ),
                        content = {
                            AppTopAppBar(
                                title = {
                                    Text(
                                        text = stringResource(id = R.string.app_name),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                navigationIcon = {
                                    AppIconButton(
                                        onClick = appState::navigateToSearch,
                                        icon = {
                                            Icon(
                                                imageVector = AppIcons.Search,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    )
                                },
                                actions = {
                                    AppIconButton(
                                        onClick = appState::navigateToSettings,
                                        icon = {
                                            Icon(
                                                imageVector = AppIcons.Settings,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    )
                                },
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = Color.Transparent
                                )
                            )
                        }
                    )

                    Box(
                        modifier = Modifier.consumeWindowInsets(
                            if (shouldShowTopAppBar) {
                                WindowInsets.safeDrawing.only(sides = WindowInsetsSides.Top)
                            } else {
                                WindowInsets(left = 0, top = 0, right = 0, bottom = 0)
                            },
                        ),
                        content = {
                            AppNavHost(
                                appState = appState,
                                onShowSnackbar = { message, action ->
                                    snackbarHostState.showSnackbar(
                                        message = message,
                                        actionLabel = action,
                                        duration = SnackbarDuration.Short,
                                    ) == SnackbarResult.ActionPerformed
                                },
                                windowAdaptiveInfo = windowAdaptiveInfo
                            )
                        }
                    )
                }
            )
        }
    )
}
