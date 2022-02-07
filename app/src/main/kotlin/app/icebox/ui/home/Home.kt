package app.icebox.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import app.icebox.Packages.getDisablePackages
import app.icebox.Packages.longPressDisableIcon
import app.icebox.Packages.tapDisableIcon
import app.icebox.R
import app.icebox.ui.layout.TopBar

@ExperimentalFoundationApi
@Composable
fun Home(navController: NavController) {
    val disablePackages = getDisablePackages()
    Scaffold(
        topBar = {
            TopBar {
                navController.navigate("add")
            }
        },
        content = {
            if (disablePackages != null) {
                DisablePackageInfoGrid(iconsInfo = disablePackages)
            } else {
                Text(text = "please add disable package")
            }

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
                                tapDisableIcon(disablePackages = disablePackages)
                            },
                            onDoubleTap = {
                            },
                            onLongPress = {
                                longPressDisableIcon(disablePackages = disablePackages)
                            })
                    })
            }
        }
    )
}

