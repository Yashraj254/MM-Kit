package com.yashraj.music_presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SheetContent(
    content: @Composable() (BoxScope.() -> Unit)
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        content()
    }
}