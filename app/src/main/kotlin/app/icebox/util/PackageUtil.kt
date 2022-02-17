package app.icebox.util

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.compose.foundation.ExperimentalFoundationApi
import app.icebox.MainActivity
import app.icebox.R
import app.icebox.Shell.setAppDisabledAsUser
import app.icebox.util.Toast.showToast
import app.icebox.data.DisabledPackage
import app.icebox.data.InstalledPackage
import app.icebox.util.FileUtil.deleteFileOrDir
import java.io.File

@ExperimentalFoundationApi
object PackageUtil {
    private val app = MainActivity.app
    private val packageManager = app.packageManager

    /**
     * 获取 PackageInfo
     * @param packageName 包名
     * @return 返回 PackageInfo 或 null
     */
    fun getPackageInfoOrNull(packageName: String): PackageInfo? {
        return packageManager.getPackageInfo(packageName, PackageManager.MATCH_UNINSTALLED_PACKAGES)
    }

    /**
     * 获取 ApplicationInfo
     * @param packageName 包名
     * @return 返回 ApplicationInfo 或 null
     */
    fun getApplicationInfoOrNull(packageName: String): ApplicationInfo? {
        return getPackageInfoOrNull(packageName)?.applicationInfo
    }

    /**
     * 判断应用状态
     * @param packageName 应用包名
     * @return 返回 Boolean 或 null
     */
    fun getApplicationIsEnable(packageName: String): Boolean? {
        return getApplicationInfoOrNull(packageName)?.enabled
    }

    /**
     * 获取已安装应用列表
     * @return 已安装的应用列表
     */
    fun getInstalledPackages(): List<InstalledPackage> {
        val installedPackages = mutableListOf<InstalledPackage>()
        packageManager.getInstalledPackages(0).forEach {
            if (it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                val applicationLabel = it.applicationInfo.loadLabel(packageManager).toString()
                val applicationIcon = it.applicationInfo.loadIcon(packageManager)
                val packageName = it.packageName
                installedPackages.add(
                    InstalledPackage(
                        applicationLabel, applicationIcon, packageName
                    )
                )
            }
        }
        installedPackages.sortBy {
            it.packageName.lowercase().replace(".", "_")
        }
        return installedPackages
    }

    /**
     * 删除 disabledPackages 文件
     * @return 文件不存在返回 true
     */
    fun deleteDisabledPackages(): Boolean {
        val file = File(app.filesDir, "disabledPackages")
        return deleteFileOrDir(file)
    }

    /**
     * 保存 disabledPackages 到文件
     * @param disabledPackages 停用应用列表
     */
    fun saveDisabledPackages(disabledPackages: List<String>) {
        val file = File(app.filesDir, "disabledPackages")
        disabledPackages.forEach {
            file.appendText(it + "\n")
        }
    }

    /**
     * 从文件加载停用应用列表
     * @return 停用应用列表
     */
    fun loadDisabledPackages(): List<String> {
        val disabledPackages = mutableListOf<String>()
        val file = File(app.filesDir, "disabledPackages")
        return if (file.exists()) {
            file.readLines()
        } else {
            disabledPackages
        }
    }

    fun getDisabledPackages(): List<DisabledPackage> {
        val disabledPackages = mutableListOf<DisabledPackage>()
        loadDisabledPackages().forEach {
            val fileName = it.lowercase().replace(".", "_")
            val file = File("${app.filesDir.absolutePath}/icon/${fileName}.png")
            disabledPackages.add(
                DisabledPackage(
                    it, file
                )
            )
        }
        disabledPackages.sortBy {
            it.packageName.lowercase().replace(".", "_")
        }
        return disabledPackages
    }

    /**
     * 打开应用
     * @param packageName 应用包名
     */
    fun launchApplication(packageName: String) =
        app.startActivity(packageManager.getLaunchIntentForPackage(packageName))

    fun backToHome() {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
        }
        app.startActivity(intent)
    }

    fun tapDisabledPackageCard(packageName: String) {
        backToHome()
        when (getApplicationIsEnable(packageName)) {
            true -> {
                launchApplication(packageName)
            }
            false -> {
                setAppDisabledAsUser(packageName, false)
                launchApplication(packageName)
            }
            null -> {
            }
        }
    }

    fun doubleTapDisabledPackageCard(packageName: String) {
        when (getApplicationIsEnable(packageName)) {
            true -> {
                setAppDisabledAsUser(packageName, true)
                showToast(
                    packageName, R.string.toast_disable
                )
            }
            false -> {
                showToast("已经冻结了，管不住手是吧！！！")
            }
            null -> {
            }
        }
    }

    fun tapFloatingActionButton(disabledPackages: List<DisabledPackage>) {
        disabledPackages.forEach {
            when (getApplicationIsEnable(it.packageName)) {
                true -> {
                    setAppDisabledAsUser(it.packageName, true)
                }
                false -> {
                }
                null -> {
                }
            }
        }
        showToast("全部冻结")
        backToHome()
    }

    fun longPressFloatingActionButton(disabledPackages: List<DisabledPackage>) {
        disabledPackages.forEach {
            when (getApplicationIsEnable(it.packageName)) {
                true -> {
                }
                false -> {
                    setAppDisabledAsUser(it.packageName, false)
                }
                null -> {
                }
            }
        }
        showToast("全部解冻")
        backToHome()
    }
}