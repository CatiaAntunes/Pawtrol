package com.example.pawtrolapp.android.auth.login

import androidx.compose.runtime.Composable
import com.example.pawtrolapp.android.destinations.FeedDestination
import com.example.pawtrolapp.android.destinations.LoginDestination
import com.example.pawtrolapp.android.destinations.SignUpDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun Login(
    navigator: DestinationsNavigator
) {
    val viewModel: LoginViewModel = koinViewModel()
    LoginScreen(
        uiState = viewModel.uiState,
        onEmailChange = viewModel::updateEmail,
        onPasswordChange = viewModel::updatePassword,
        onSignInClick = viewModel::signIn,
        onNavigateToFeed = {
            navigator.navigate(FeedDestination)
        },
        onNavigateToSignUp = {
            navigator.navigate(SignUpDestination.route){
                popUpTo(LoginDestination.route){
                    inclusive = true
                }
            }
        }
    )
}