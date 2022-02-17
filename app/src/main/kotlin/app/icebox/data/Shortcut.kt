package app.icebox.data

import android.content.Intent
import java.io.File

data class Shortcut(
    val packageName: String,
    val icon: File,
    val intent: Intent
)