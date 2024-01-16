package com.example.pawtrolapp.android.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel(private val firebaseAuth: FirebaseAuth) : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun signIn() {
        viewModelScope.launch {
            uiState = uiState.copy(isAuthenticating = true)
            try {
                val result = firebaseAuth.signInWithEmailAndPassword(uiState.email, uiState.password).await()
                val user = result.user
                uiState = if (user != null) {
                    // User is signed in
                    LoginUiState(
                        isAuthenticating = false,
                        authenticationSucceeded = true
                    )
                } else {
                    // User is null, handle error
                    LoginUiState(
                        isAuthenticating = false,
                        authErrorMessage = "Authentication failed."
                    )
                }
            } catch (e: Exception) {
                uiState = LoginUiState(
                    isAuthenticating = false,
                    authErrorMessage = e.localizedMessage
                )
            }
        }
    }

    fun updateEmail(input: String) {
        uiState = uiState.copy(email = input)
    }

    fun updatePassword(input: String) {
        uiState = uiState.copy(password = input)
    }
}

data class LoginUiState(
    var email: String = "",
    var password: String = "",
    var isAuthenticating: Boolean = false,
    var authErrorMessage: String? = null,
    var authenticationSucceeded: Boolean = false
)

