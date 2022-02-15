package app.icebox.ui.add

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import app.icebox.InstalledApplication

@ExperimentalFoundationApi
@Composable
fun InstalledApplicationColumn(
    installedApplications: MutableList<InstalledApplication>,
    booleanArray: BooleanArray,
    export: MutableList<String>
) {
    LazyColumn(
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(installedApplications) { installedApplication ->
            val index = installedApplications.indexOf(installedApplication)
            val checked = rememberSaveable { mutableStateOf(booleanArray[index]) }
            InstalledApplicationCard(
                installedApplication = installedApplication,
                checked = checked.value,
                onCheckedChange = {
                    checked.value = !checked.value
                    booleanArray[index] = checked.value
                    if (checked.value) {
                        export.add(installedApplication.packageName)
                    } else {
                        export.remove(installedApplication.packageName)
                    }
                })
        }
    }
}