package app.icebox.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.icebox.Shortcut
import app.icebox.util.ShortcutUtil.start

@ExperimentalFoundationApi
@Composable
fun ShortcutCard(shortcut: Shortcut) {
    Image(
        painter = painterResource(shortcut.icon),
        contentDescription = null,
        modifier = Modifier
            .size(65.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        start(shortcut = shortcut)
                    }
                )
            }
    )
}