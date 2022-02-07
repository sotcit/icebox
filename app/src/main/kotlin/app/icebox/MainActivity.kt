package app.icebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.icebox.ui.add.Add
import app.icebox.ui.home.Home
import app.icebox.ui.theme.IceBoxTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = this
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            IceBoxTheme {
                ProvideWindowInsets {
                    rememberSystemUiController().setStatusBarColor(
                        MaterialTheme.colors.primary,
                        darkIcons = MaterialTheme.colors.isLight
                    )
                    Column {
                        Spacer(
                            modifier = Modifier
                                .statusBarsHeight()
                                .fillMaxWidth()
                        )
                        val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") {
                                Home(navController = navController)
                            }
                            composable("add") {
                                Add(navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        lateinit var app: MainActivity private set
    }
}