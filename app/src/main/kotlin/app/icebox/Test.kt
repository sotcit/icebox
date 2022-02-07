package app.icebox

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import app.icebox.Packages.getApplicationIsEnable
import app.icebox.Shell.setAppDisabledAsUser

@ExperimentalFoundationApi
class Test : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val packageName = "com.unionpay"
        when (getApplicationIsEnable(packageName)) {
            true -> {
            }
            false -> {
                setAppDisabledAsUser(packageName, false)
            }
            null -> {
            }
        }
        val uri = Uri.parse("upwallet://native/codepay")
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
//        val packageName = "com.tencent.mm"
//        when (getApplicationIsEnable(packageName)) {
//            true -> {
//            }
//            false -> {
//                setAppDisabledAsUser(packageName, false)
//            }
//            null -> {
//            }
//        }
//        val intent = Intent("com.tencent.mm.ui.ShortCutDispatchAction").apply {
//            putExtra("LauncherUI.Shortcut.LaunchType", "launch_type_offline_wallet")
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        }
        startActivity(intent)
        finish()
    }
}

