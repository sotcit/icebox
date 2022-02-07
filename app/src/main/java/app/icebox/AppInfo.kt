package app.icebox

import android.graphics.drawable.Drawable

/**
 * @param applicationLabel 应用标签
 * @param packageName 应用包名
 * @param applicationIcon 应用图标
 */
data class AppInfo(
    val applicationLabel: String,
    var applicationIcon: Drawable,
    val drawable: String,
    var packageName: String,
)