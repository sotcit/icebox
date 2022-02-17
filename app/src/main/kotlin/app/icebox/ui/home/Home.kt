package app.icebox.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import app.icebox.R
import app.icebox.ui.layout.TopBar
import app.icebox.util.PackageUtil.getDisabledPackages
import app.icebox.util.PackageUtil.longPressFloatingActionButton
import app.icebox.util.PackageUtil.tapFloatingActionButton
import app.icebox.util.ShortcutUtil.getShortcuts

@ExperimentalFoundationApi
@Composable
fun Home(navController: NavController) {
    val disabledPackages = getDisabledPackages()
    val shortcuts = getShortcuts()
    Scaffold(
        topBar = {
            TopBar {
                navController.navigate("add")
            }
        },
        content = {
            IconGrid(disabledPackages, shortcuts)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                backgroundColor = Color(0xFF167C80)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_ac_snow),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                tapFloatingActionButton(disabledPackages)
                            },
                            onDoubleTap = {
                            },
                            onLongPress = {
                                longPressFloatingActionButton(disabledPackages)
                            })
                    })
            }
        }
    )
}

