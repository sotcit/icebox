package app.icebox

import java.io.File

data class DisablePackageInfo(
    val drawable: String,
    var packageName: String,
    val file: File
)
