package app.icebox.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import app.icebox.data.Shortcut
import app.icebox.util.ShortcutUtil.startShortcut
import coil.compose.rememberImagePainter

@ExperimentalFoundationApi
@Composable
fun ShortcutCard(shortcut: Shortcut) {
    Image(
        painter = rememberImagePainter(shortcut.icon),
        contentDescription = null,
        modifier = Modifier
            .size(65.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        startShortcut(shortcut = shortcut)
                    }
                )
            }
    )
}