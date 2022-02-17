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
import app.icebox.data.InstalledPackage

@ExperimentalFoundationApi
@Composable
fun InstalledApplicationColumn(
    installedPackages: List<InstalledPackage>,
    booleanArray: BooleanArray,
    export: MutableList<String>
) {
    LazyColumn(
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(installedPackages) { installedPackage ->
            val index = installedPackages.indexOf(installedPackage)
            val checked = rememberSaveable { mutableStateOf(booleanArray[index]) }
            InstalledApplicationCard(
                installedPackage = installedPackage,
                checked = checked.value,
                onCheckedChange = {
                    checked.value = !checked.value
                    booleanArray[index] = checked.value
                    if (checked.value) {
                        export.add(installedPackage.packageName)
                    } else {
                        export.remove(installedPackage.packageName)
                    }
                })
        }
    }
}