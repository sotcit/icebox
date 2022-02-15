package app.icebox.ui.add

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import app.icebox.Packages.applyIconPack
import app.icebox.Packages.deleteDisablePackageList
import app.icebox.Packages.deleteIconDir
import app.icebox.Packages.getInstalledPackages
import app.icebox.Packages.loadDisablePackageList
import app.icebox.Packages.saveDisablePackageList
import app.icebox.Packages.saveIcon
import app.icebox.ui.layout.FloatingButton
import app.icebox.ui.layout.TopBar

@ExperimentalFoundationApi
@Composable
fun Add(navController: NavController) {
    val disablePackageList = loadDisablePackageList()
    val installedPackages = applyIconPack(getInstalledPackages())
    val booleanArray = BooleanArray(installedPackages.size)
    val export = mutableListOf<String>()
    if (disablePackageList != null) {
        for (disablePackage in disablePackageList) {
            installedPackages.forEach {
                if (disablePackage == it.packageName) {
                    val index = installedPackages.indexOf(it)
                    booleanArray[index] = true
                }
            }
            export.add(disablePackage)
        }
    }
    Scaffold(
        topBar = {
            TopBar {
                navController.navigate("home")
            }
        },
        content = {
            InstalledApplicationColumn(
                installedApplications = installedPackages,
                booleanArray = booleanArray,
                export = export
            )
        },
        floatingActionButton = {
            FloatingButton {
                deleteDisablePackageList()
                deleteIconDir()
                saveDisablePackageList(export)
                for (installedPackage in installedPackages) {
                    export.forEach {
                        if (installedPackage.packageName == it) {
                            saveIcon(installedPackage.drawable, installedPackage.applicationIcon)
                        }
                    }
                }
            }
        }
    )
}

