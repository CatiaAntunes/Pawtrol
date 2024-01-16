package com.example.pawtrolapp.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.pawtrolapp.android.common.components.AppBar
import com.example.pawtrolapp.android.destinations.HomeDestination
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.utils.currentDestinationAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PawtrolApp(
) {
    val navHostController = rememberNavController()
    val currentDestination = navHostController.currentDestinationAsState().value
    val scaffoldState = rememberBottomSheetScaffoldState()
    val systemUiController = rememberSystemUiController()
    val isSystemInDark = isSystemInDarkTheme()
    val statusBarColor = if (isSystemInDark){
        MaterialTheme.colorScheme.surface
    } else {
        MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
    }

    val startDestinationRoute = if (FirebaseAuth.getInstance().currentUser != null){
        NavGraphs.root.startRoute.route
    } else {
        HomeDestination.route
    }
    SideEffect {
        systemUiController.setStatusBarColor(color = statusBarColor, darkIcons = !isSystemInDark)
    }

    Scaffold (
        snackbarHost = {scaffoldState},
        topBar = {
            if (currentDestination != null) {
                if (currentDestination.route != HomeDestination.route) {
                    AppBar(navHostController = navHostController)
                } else {
                    null
                }
            }
        }
    ){innerPaddings ->
        LaunchedEffect(Unit) {
            navHostController.navigate(startDestinationRoute){
                popUpTo(navHostController.graph.findStartDestination().id){
                    inclusive = true
                }
            }
        }
        DestinationsNavHost(
            modifier = Modifier.padding(innerPaddings),
            navGraph = NavGraphs.root,
            navController = navHostController
        )

    }


}