package app.icebox

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import app.icebox.Shell.setAppDisabledAsUser
import org.xmlpull.v1.XmlPullParser
import java.io.File
import java.io.OutputStream

@ExperimentalFoundationApi
object Packages {
    private val app = MainActivity.app
    private val packageManager = app.packageManager

    /**
     * 获取 packageInfo
     * @param packageName 应用包名
     * @return 返回 packageInfo 或 null
     */
    private fun getPackageInfoOrNull(packageName: String) = try {
        packageManager.getPackageInfo(packageName, PackageManager.MATCH_UNINSTALLED_PACKAGES)
    } catch (t: Throwable) {
        null
    }

    /**
     * 获取 applicationInfo
     * @param packageName 应用包名
     * @return 返回 applicationInfo 或 null
     */
    fun getApplicationInfoOrNull(packageName: String) =
        getPackageInfoOrNull(packageName)?.applicationInfo

    /**
     * 判断应用状态
     * @param packageName 应用包名
     * @return 返回 Boolean 或 null
     */
    fun getApplicationIsEnable(packageName: String) = getApplicationInfoOrNull(packageName)?.enabled

    /**
     * 打开应用
     * @param packageName 应用包名
     */
    fun launchApplication(packageName: String) =
        app.startActivity(packageManager.getLaunchIntentForPackage(packageName))

    fun getInstalledPackages(): MutableList<InstalledApplication> {
        val installedAppsInfo = mutableListOf<InstalledApplication>()
        packageManager.getInstalledPackages(0).forEach {
            if (it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                val applicationLabel = it.applicationInfo.loadLabel(packageManager).toString()
                val applicationIcon = it.applicationInfo.loadIcon(packageManager)
                val packageName = it.packageName
                val drawable = packageName.lowercase().replace(".", "_")
                installedAppsInfo.add(
                    InstalledApplication(
                        applicationLabel = applicationLabel,
                        applicationIcon = applicationIcon,
                        drawable = drawable,
                        packageName = packageName
                    )
                )
            }
        }
        installedAppsInfo.sortBy {
            it.drawable
        }
        return installedAppsInfo
    }

    /**
     * 使用图标包
     */
    fun applyIconPack(aPackages: MutableList<InstalledApplication>): MutableList<InstalledApplication> {
        val iconPack =
            app.packageManager.getResourcesForApplication("app.iconpack")
        val identifier = iconPack.getIdentifier("appfilter", "xml", "app.iconpack")
        val xmlResourceParser = iconPack.getXml(identifier)
        while (xmlResourceParser.eventType != XmlPullParser.END_DOCUMENT) {
            when (xmlResourceParser.eventType) {
                XmlPullParser.START_TAG -> {
                    if (xmlResourceParser.name.equals("item")) {
                        val drawable = xmlResourceParser.getAttributeValue(null, "drawable")
                        val packageName =
                            xmlResourceParser.getAttributeValue(null, "packageName")
                        val applicationIconId =
                            iconPack.getIdentifier(drawable, "drawable", "app.iconpack")
                        val applicationIcon =
                            ResourcesCompat.getDrawable(iconPack, applicationIconId, null)
                        aPackages.forEach {
                            if (packageName == it.packageName) {
                                if (applicationIcon != null) {
                                    it.applicationIcon = applicationIcon
                                }
                            }
                        }
                    }
                }
            }
            xmlResourceParser.next()
        }
        aPackages.sortBy {
            it.drawable
        }
        return aPackages
    }

    /**
     * 删除存储冻结应用列表的文件
     */
    fun deleteDisablePackageList(): Boolean {
        val file = File(app.filesDir, "export")
        return if (file.exists()) {
            file.delete()
        } else {
            true
        }
    }

    /**
     * 保存冻结应用列表到文件
     */
    fun saveDisablePackageList(export: MutableList<String>) {
        export.sort()
        val file = File(app.filesDir, "export")
        export.forEach {
            file.appendText(it + "\n")
        }
    }

    /**
     * 从文件加载冻结应用列表
     */
    fun loadDisablePackageList(): List<String>? {
        val file = File(app.filesDir, "export")
        return if (file.exists()) {
            file.readLines()
        } else {
            null
        }
    }

    fun getDisablePackages(): MutableList<DisabledApplication>? {
        if (loadDisablePackageList() != null) {
            val disablePackagesInfo = mutableListOf<DisabledApplication>()
            loadDisablePackageList()?.forEach {
                val drawable = it.lowercase().replace(".", "_")
                val packageName = it
                val file = File("${app.filesDir.absolutePath}/icon/${drawable}.png")
                disablePackagesInfo.add(
                    DisabledApplication(drawable = drawable, packageName = packageName, file = file)
                )
            }
            disablePackagesInfo.sortBy {
                it.drawable
            }
            return disablePackagesInfo
        } else {
            return null
        }
    }

    /**
     * 删除 icon 文件夹
     */
    fun deleteIconDir(): Boolean {
        val iconDir = File("${app.filesDir.absolutePath}/icon")
        val listFiles = iconDir.listFiles()
        if (listFiles != null)
            for (file in listFiles) {
                if (file.isFile) {
                    file.delete()
                }
            }
        return iconDir.delete()
    }

    /**
     * 保存 icon 到 icon 文件夹
     */
    fun saveIcon(drawable: String, icon: Drawable) {
        val iconDir = File("${app.filesDir.absolutePath}/icon")
        iconDir.mkdirs()
        val file = File("${iconDir}/${drawable}.png")
        val fos: OutputStream = file.outputStream()
        val toBitmap = icon.toBitmap()
        toBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.flush()
        fos.close()
    }

    fun backToHome() {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
        }
        app.startActivity(intent)
    }

    fun tapDisabledApplicationCard(disabledApplication: DisabledApplication) {
        backToHome()
        when (getApplicationIsEnable(disabledApplication.packageName)) {
            true -> {

                launchApplication(disabledApplication.packageName)
            }
            false -> {
                setAppDisabledAsUser(disabledApplication.packageName, false)
                launchApplication(disabledApplication.packageName)
            }
            null -> {
            }
        }
    }

    fun doubleTapDisabledApplicationCard(disabledApplication: DisabledApplication) {
        when (getApplicationIsEnable(disabledApplication.packageName)) {
            true -> {
                setAppDisabledAsUser(disabledApplication.packageName, true)
                Toast.showToast(
                    disabledApplication.packageName,
                    R.string.toast_disable
                )
            }
            false -> {
                Toast.showToast("已经冻结了，管不住手是吧！！！")
            }
            null -> {
            }
        }
    }

    fun tapFloatingActionButton(disabledApplications: MutableList<DisabledApplication>?) {
        if (disabledApplications != null) {
            disabledApplications.forEach {
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
            Toast.showToast("全部冻结")
            backToHome()
        } else {
            Toast.showToast("请添加应用")
        }
    }

    fun longPressFloatingActionButton(disabledApplications: MutableList<DisabledApplication>?) {
        if (disabledApplications != null) {
            disabledApplications.forEach {
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
            Toast.showToast("全部解冻")
            backToHome()
        } else {
            Toast.showToast("请添加应用")
        }
    }


}