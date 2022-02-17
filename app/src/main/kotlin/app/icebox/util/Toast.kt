package app.icebox.util

import android.widget.Toast
import android.widget.Toast.makeText
import androidx.compose.foundation.ExperimentalFoundationApi
import app.icebox.MainActivity
import app.icebox.util.PackageUtil.getApplicationInfoOrNull

@ExperimentalFoundationApi
object Toast {
    private val app = MainActivity.app
    private val packageManager = app.packageManager

    /**
     * 展示操作状态
     * @param packageName 被操作应用的包名
     * @param resId 操作返回码
     */
    fun showToast(packageName: String, resId: Int) {
        makeText(
            app,
            app.getString(resId, getApplicationInfoOrNull(packageName)?.loadLabel(packageManager)),
            Toast.LENGTH_SHORT
        ).show()
    }

    fun showToast(message: String) {
        makeText(
            app,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}