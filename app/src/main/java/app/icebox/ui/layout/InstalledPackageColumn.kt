package app.icebox.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import app.icebox.AppInfo

@Composable
fun InstalledPackageColumn(
    iconsInfo: MutableList<AppInfo>,
    booleanArray: BooleanArray,
    export: MutableList<String>
) {
    LazyColumn(
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(iconsInfo) { iconInfo ->
            val index = iconsInfo.indexOf(iconInfo)
            val checked = rememberSaveable { mutableStateOf(booleanArray[index]) }
            InstalledPackageInfoCard(
                appInfo = iconInfo,
                checked = checked.value,
                onCheckedChange = {
                    checked.value = !checked.value
                    booleanArray[index] = checked.value
                    if (checked.value) {
                        export.add(iconInfo.packageName)
                    } else {
                        export.remove(iconInfo.packageName)
                    }
                })
        }
    }
}