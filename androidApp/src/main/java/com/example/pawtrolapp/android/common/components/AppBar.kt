package com.example.pawtrolapp.android.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pawtrolapp.android.R
import com.example.pawtrolapp.android.common.theming.DarkColors
import com.example.pawtrolapp.android.common.theming.LightColors
import com.example.pawtrolapp.android.common.theming.SmallElevation
import com.example.pawtrolapp.android.common.theming.isLight
import com.example.pawtrolapp.android.destinations.CreatePostDestination
import com.example.pawtrolapp.android.destinations.FeedDestination
import com.example.pawtrolapp.android.destinations.HomeDestination
import com.example.pawtrolapp.android.destinations.LoginDestination
import com.example.pawtrolapp.android.destinations.PostDetailDestination
import com.example.pawtrolapp.android.destinations.SignUpDestination
import com.google.firebase.auth.FirebaseAuth
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.utils.currentDestinationAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
){
    val configuration = LocalConfiguration.current
    val currentDestination = navHostController.currentDestinationAsState().value
    val showDialog = remember { mutableStateOf(false) }
    val screenWidth = configuration.screenWidthDp.dp
    if (showDialog.value) {
        ShowLogoutConfirmationDialog(navHostController, showDialog)
    }
    Surface(
        modifier = modifier,
        tonalElevation = SmallElevation,
        shadowElevation = SmallElevation
    ) {
        TopAppBar(
            title = {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ){
                    Image(
                        painter = painterResource(id = R.drawable.logo_round),
                        contentDescription = null,
                        Modifier
                            .size(width = 40.dp, height = 40.dp)
                            .padding(0.dp, 5.dp, 5.dp, 0.dp)
                    )
                    if (screenWidth > 400.dp) {
                        Text(text = stringResource(id = getAppBarTitle(currentDestination?.route)))
                    }
                }
            },

            modifier = modifier,
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            actions = {
                AnimatedVisibility(visible = currentDestination?.route == FeedDestination.route) {
                    Row (
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ){
                        IconButton(onClick = { navHostController.navigate(CreatePostDestination) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_circle_24px),
                                contentDescription = null,
                                Modifier.size(30.dp),
                                tint = if (MaterialTheme.colorScheme.isLight()){
                                    LightColors.primary
                                } else {
                                    DarkColors.secondary
                                },
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(id = R.drawable.search_24px),
                                contentDescription = null,
                                Modifier.size(30.dp)
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(id = R.drawable.map_24px),
                                contentDescription = null,
                                Modifier.size(30.dp)
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(id = R.drawable.swap_vert_24px),
                                contentDescription = null,
                                Modifier.size(30.dp)
                            )
                        }
                        IconButton(onClick =
                        {
                            showDialog.value = true
                            FirebaseAuth.getInstance().signOut()
                        }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.logout_24px),
                                contentDescription = null,
                                Modifier.size(30.dp),
                                tint = if (MaterialTheme.colorScheme.isLight()){
                                    LightColors.primary
                                } else {
                                    DarkColors.secondary
                                }
                            )
                        }

                    }
                }

            },
            navigationIcon = {
                if (shouldShowNavigationIcon(currentDestination?.route)){
                    IconButton(onClick = { navHostController.navigateUp()}) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_arrow_back),
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }
}

private fun getAppBarTitle(currentDestinationRoute: String?): Int{
    return when(currentDestinationRoute){
        LoginDestination.route -> R.string.login_destination_title
        SignUpDestination.route -> R.string.signup_destination_title
        FeedDestination.route -> R.string.home_destination_title
        PostDetailDestination.route -> R.string.post_detail_destination_title
        CreatePostDestination.route -> R.string.create_post
        else -> R.string.no_destination_title
    }
}

private fun shouldShowNavigationIcon(currentDestinationRoute: String?): Boolean{
    return !(
            currentDestinationRoute == FeedDestination.route
            )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowLogoutConfirmationDialog(
    navHostController: NavHostController,
    showDialog: MutableState<Boolean>
) {

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Text(text = stringResource(R.string.logout_confirmation_title))
            },
            text = {
                Text(text = stringResource(R.string.logout_confirmation_message))
            },
            confirmButton = {
                Button(
                    onClick = {
                        navHostController.navigate(HomeDestination.route)
                        showDialog.value = false
                    },
                ) {
                    Text(text = stringResource(R.string.logout_confirm_button))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                    },
                ) {
                    Text(text = stringResource(R.string.logout_cancel_button))
                }
            },
            icon = {
                Icon(imageVector = Icons.Default.Info, contentDescription = null)
            }
        )
    }
}