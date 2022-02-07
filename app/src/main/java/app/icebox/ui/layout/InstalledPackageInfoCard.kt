package app.icebox.ui.layout

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
import app.icebox.AppInfo
import coil.compose.rememberImagePainter

@Composable
fun InstalledPackageInfoCard(
    appInfo: AppInfo,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = rememberImagePainter(appInfo.applicationIcon),
            contentDescription = null,
            modifier = Modifier
                .size(55.dp)
        )
        Column(
            modifier = Modifier.width(255.dp)
        ) {
            Text(
                text = appInfo.applicationLabel,
                textAlign = TextAlign.Start
            )
            Text(
                text = appInfo.packageName,
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