package app.icebox

import android.content.Intent

data class Shortcut(
    val packageName: String,
    val icon: Int,
    val intent: Intent
)