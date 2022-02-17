package app.icebox.ui.add

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.icebox.data.InstalledPackage
import app.icebox.util.PackageUtil.getApplicationIsEnable
import coil.compose.rememberImagePainter
import coil.transform.GrayscaleTransformation

@ExperimentalFoundationApi
@Composable
fun InstalledApplicationCard(
    installedPackage: InstalledPackage,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = rememberImagePainter(
                installedPackage.applicationIcon, builder = {
                    if (getApplicationIsEnable(installedPackage.packageName) == false) {
                        transformations(GrayscaleTransformation())
                    }
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .size(55.dp)
        )
        Column(
            modifier = Modifier.width(255.dp)
        ) {
            Text(
                text = installedPackage.applicationLabel,
                textAlign = TextAlign.Start
            )
            Text(
                text = installedPackage.packageName,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Checkbox(
            checked = checked, onCheckedChange = onCheckedChange
        )
    }
}