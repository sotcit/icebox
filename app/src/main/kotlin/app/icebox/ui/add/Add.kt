package app.icebox.ui.add

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import app.icebox.ui.layout.FloatingButton
import app.icebox.ui.layout.TopBar
import app.icebox.util.FileUtil.copyFile
import app.icebox.util.FileUtil.getIconDir
import app.icebox.util.FileUtil.initFileDir
import app.icebox.util.IconPackUtil.getAppResources
import app.icebox.util.IconPackUtil.getIdentifier
import app.icebox.util.PackageUtil.deleteDisabledPackages
import app.icebox.util.PackageUtil.getInstalledPackages
import app.icebox.util.PackageUtil.loadDisabledPackages
import app.icebox.util.PackageUtil.saveDisabledPackages
import java.io.File

@ExperimentalFoundationApi
@Composable
fun Add(navController: NavController) {
    val installedPackages = getInstalledPackages()
    val disabledPackages = loadDisabledPackages()
    val booleanArray = BooleanArray(installedPackages.size)
    val export = mutableListOf<String>()
    for (disabledPackage in disabledPackages) {
        installedPackages.forEach {
            if (disabledPackage == it.packageName) {
                val index = installedPackages.indexOf(it)
                booleanArray[index] = true
            }
        }
        export.add(disabledPackage)
    }
    Scaffold(
        topBar = {
            TopBar {
                navController.navigate("home")
            }
        },
        content = {
            InstalledApplicationColumn(
                installedPackages = installedPackages,
                booleanArray = booleanArray,
                export = export
            )
        },
        floatingActionButton = {
            FloatingButton {
                deleteDisabledPackages()
                export.sort()
                saveDisabledPackages(export)
                val appResources = getAppResources("app.iconpack")
                val iconDir = getIconDir()
                initFileDir(iconDir)
                export.forEach {
                    val name = it.lowercase().replace(".", "_")
                    val identifier = getIdentifier(appResources, name, "drawable", "app.iconpack")
                    val file = File(iconDir, "${name}.png")
                    copyFile(appResources, identifier, file)
                }
                listOf(
                    "shortcut_alipay_pay",
                    "shortcut_alipay_scan",
                    "shortcut_unionpay_pay",
                    "shortcut_wechat_pay",
                    "shortcut_wechat_scan"
                ).forEach {
                    getIdentifier(appResources, it, "drawable", "app.iconpack").run {
                        copyFile(appResources, this, File(iconDir, "${it}.png"))
                    }
                }
            }
        }
    )
}

