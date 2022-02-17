package app.icebox.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import app.icebox.data.DisabledPackage
import app.icebox.util.PackageUtil.doubleTapDisabledPackageCard
import app.icebox.util.PackageUtil.tapDisabledPackageCard
import coil.compose.rememberImagePainter

@ExperimentalFoundationApi
@Composable
fun DisabledApplicationCard(disabledPackage: DisabledPackage) {
    Image(
        painter = rememberImagePainter(disabledPackage.file),
        contentDescription = null,
        modifier = Modifier
            .size(65.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        tapDisabledPackageCard(disabledPackage.packageName)
                    },
                    onDoubleTap = {
                        doubleTapDisabledPackageCard(disabledPackage.packageName)
                    },
                    onLongPress = {
                    }
                )
            }
    )
}