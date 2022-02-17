package app.icebox.util

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import app.icebox.MainActivity
import app.icebox.Shell.setAppDisabledAsUser
import app.icebox.data.Shortcut
import app.icebox.util.PackageUtil.getApplicationIsEnable
import java.io.File

@ExperimentalFoundationApi
object ShortcutUtil {
    private val app = MainActivity.app
    fun getShortcuts(): List<Shortcut> {
        val shortcutsIcon = listOf(
            "shortcut_alipay_pay",
            "shortcut_alipay_scan",
            "shortcut_unionpay_pay",
            "shortcut_wechat_pay",
            "shortcut_wechat_scan"
        )
        return listOf(
            Shortcut(
                "com.eg.android.AlipayGphone",
                File("${app.filesDir.absolutePath}/icon/${shortcutsIcon[0]}.png"),
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("alipays://platformapi/startapp?appId=20000056")
                )
            ),
            Shortcut(
                "com.eg.android.AlipayGphone",
                File("${app.filesDir.absolutePath}/icon/${shortcutsIcon[1]}.png"),
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("alipayqr://platformapi/startapp?saId=10000007")
                )
            ),
            Shortcut(
                "com.unionpay",
                File("${app.filesDir.absolutePath}/icon/${shortcutsIcon[2]}.png"),
                Intent(Intent.ACTION_VIEW, Uri.parse("upwallet://native/codepay"))
            ),
            Shortcut(
                "com.tencent.mm",
                File("${app.filesDir.absolutePath}/icon/${shortcutsIcon[3]}.png"),
                Intent("com.tencent.mm.ui.ShortCutDispatchAction").apply {
                    putExtra("LauncherUI.Shortcut.LaunchType", "launch_type_offline_wallet")
                }),
            Shortcut(
                "com.tencent.mm",
                File("${app.filesDir.absolutePath}/icon/${shortcutsIcon[4]}.png"),
                Intent("com.tencent.mm.ui.ShortCutDispatchAction").apply {
                    putExtra("LauncherUI.Shortcut.LaunchType", "launch_type_scan_qrcode")
                })
        )
    }

    fun startShortcut(shortcut: Shortcut) {
        if (getApplicationIsEnable(shortcut.packageName) == false) {
            setAppDisabledAsUser(shortcut.packageName, false)
        }
        app.startActivity(shortcut.intent)
    }
}