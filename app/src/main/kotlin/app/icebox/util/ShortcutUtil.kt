package app.icebox.util

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import app.icebox.MainActivity
import app.icebox.Packages.getApplicationIsEnable
import app.icebox.R
import app.icebox.Shell.setAppDisabledAsUser
import app.icebox.Shortcut

@ExperimentalFoundationApi
object ShortcutUtil {
    private val app = MainActivity.app
    fun getShortcuts(): List<Shortcut> {
        return listOf(
            Shortcut(
                packageName = "com.tencent.mm",
                icon = R.drawable.wechat_pay,
                intent = Intent("com.tencent.mm.ui.ShortCutDispatchAction").apply {
                    putExtra("LauncherUI.Shortcut.LaunchType", "launch_type_offline_wallet")
                }),
            Shortcut(
                packageName = "com.tencent.mm",
                icon = R.drawable.wechat_scan,
                intent = Intent("com.tencent.mm.ui.ShortCutDispatchAction").apply {
                    putExtra("LauncherUI.Shortcut.LaunchType", "launch_type_scan_qrcode")
                }),
            Shortcut(
                packageName = "com.unionpay",
                icon = R.drawable.unionpay_pay,
                intent = Intent(Intent.ACTION_VIEW, Uri.parse("upwallet://native/codepay"))
            ),
            Shortcut(
                packageName = "com.eg.android.AlipayGphone",
                icon = R.drawable.alipay_pay,
                intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("alipays://platformapi/startapp?appId=20000056")
                )
            ),
            Shortcut(
                packageName = "com.eg.android.AlipayGphone",
                icon = R.drawable.alipay_scan,
                intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("alipayqr://platformapi/startapp?saId=10000007")
                )
            )
        )
    }

    fun start(shortcut: Shortcut) {
        if (getApplicationIsEnable(shortcut.packageName) == false) {
            setAppDisabledAsUser(shortcut.packageName, false)
        }
        app.startActivity(shortcut.intent)
    }
}