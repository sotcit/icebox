package app.icebox

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.IBinder
import android.system.Os
import androidx.compose.foundation.ExperimentalFoundationApi
import rikka.shizuku.ShizukuBinderWrapper
import rikka.shizuku.SystemServiceHelper

@ExperimentalFoundationApi
object Shell {
    /**
     * 使用 Shizuku 冻结、解冻应用
     * @param packageName 要操作的应用
     * @param disabled true 冻结应用，false 解冻应用
     */
    @SuppressLint("PrivateApi")
    fun setAppDisabledAsUser(packageName: String, disabled: Boolean) {
        try {
            val proxy = Class.forName("android.content.pm.IPackageManager\$Stub")
                .getMethod("asInterface", IBinder::class.java)
                .invoke(null, ShizukuBinderWrapper(SystemServiceHelper.getSystemService("package")))
            proxy::class.java.getMethod(
                "setApplicationEnabledSetting",
                String::class.java,
                Int::class.java,
                Int::class.java,
                Int::class.java,
                String::class.java
            ).invoke(
                proxy, packageName,
                if (disabled) PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                else PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                0, Os.getuid() / 100000, BuildConfig.APPLICATION_ID
            )
        } catch (t: Throwable) {
        }
    }
}