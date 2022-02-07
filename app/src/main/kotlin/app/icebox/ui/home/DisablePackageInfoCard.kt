package app.icebox.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import app.icebox.DisablePackageInfo
import app.icebox.Packages.doubleTapDisablePackageInfoCard
import app.icebox.Packages.tapDisablePackageInfoCard
import coil.compose.rememberImagePainter

@ExperimentalFoundationApi
@Composable
fun DisablePackageInfoCard(disablePackagesInfo: DisablePackageInfo) {
    Image(
        painter = rememberImagePainter(disablePackagesInfo.file),
        contentDescription = null,
        modifier = Modifier
            .size(65.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        tapDisablePackageInfoCard(disablePackagesInfo = disablePackagesInfo)
                    },
                    onDoubleTap = {
                        doubleTapDisablePackageInfoCard(disablePackagesInfo = disablePackagesInfo)
                    },
                    onLongPress = {
                    }
                )
            }
    )
}