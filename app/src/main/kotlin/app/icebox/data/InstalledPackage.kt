package app.icebox.data

import android.graphics.drawable.Drawable

data class InstalledPackage(
    val applicationLabel: String,
    var applicationIcon: Drawable,
    var packageName: String
)
