package com.example.pawtrolapp.android.auth.home

import androidx.compose.runtime.Composable
import com.example.pawtrolapp.android.destinations.LoginDestination
import com.example.pawtrolapp.android.destinations.SignUpDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination //(start = true)
@Composable
fun Home(
    navigator: DestinationsNavigator
) {
    HomeScreen(
        onNavigateToLogin = {
            navigator.navigate(LoginDestination)
        },
        onNavigateToSignUp = {
            navigator.navigate(SignUpDestination)
        }
    )
}