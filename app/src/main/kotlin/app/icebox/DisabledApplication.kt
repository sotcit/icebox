package app.icebox

import java.io.File

data class DisabledApplication(
    val drawable: String,
    var packageName: String,
    val file: File
)
