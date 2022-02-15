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
import app.icebox.InstalledApplication
import app.icebox.Packages.getApplicationIsEnable
import coil.compose.rememberImagePainter
import coil.transform.GrayscaleTransformation

@ExperimentalFoundationApi
@Composable
fun InstalledApplicationCard(
    installedApplication: InstalledApplication,
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
                installedApplication.applicationIcon, builder = {
                    if (getApplicationIsEnable(installedApplication.packageName) == false) {
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
                text = installedApplication.applicationLabel,
                textAlign = TextAlign.Start
            )
            Text(
                text = installedApplication.packageName,
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