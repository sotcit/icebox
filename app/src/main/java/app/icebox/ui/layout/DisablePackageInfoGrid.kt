package app.icebox.ui.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.icebox.DisablePackageInfo

@ExperimentalFoundationApi
@Composable
fun DisablePackageInfoGrid(iconsInfo: List<DisablePackageInfo>) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(count = 4),
        contentPadding = PaddingValues(10.dp, 25.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxHeight()
    ) {
        items(iconsInfo) { iconInfo ->
            DisablePackageInfoCard(disablePackagesInfo = iconInfo)
        }
    }
}