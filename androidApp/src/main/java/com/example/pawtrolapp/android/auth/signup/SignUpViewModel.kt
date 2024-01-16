package com.example.pawtrolapp.android.auth.signup

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawtrolapp.android.common.datastore.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignUpViewModel(private val firebaseAuth: FirebaseAuth) : ViewModel() {
    var uiState by mutableStateOf(SignUpUiState())
        private set

    private fun saveUserToFirestore(userData: User) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(userData.userId)
            .set(userData)
            .addOnSuccessListener {
                Log.d("Firestore", "Document created")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore Error", "Error writing document", e)
            }
    }
    fun signUp() {
        viewModelScope.launch {
            uiState = uiState.copy(isAuthenticating = true)
            try {
                val result = firebaseAuth.createUserWithEmailAndPassword(uiState.email, uiState.password).await()
                val firebaseUser = result.user
                if (firebaseUser != null) {
                    val userData = User(
                        userId = firebaseUser.uid,
                        username = uiState.username,
                        email = firebaseUser.email ?: "",
                        profileUrl = "https://picsum.photos/200"
                    )
                    saveUserToFirestore(userData)
                    uiState = SignUpUiState(isAuthenticating = false, authenticationSucceeded = true)
                } else {
                    uiState = SignUpUiState(isAuthenticating = false, authErrorMessage = "Sign up failed.")
                }
            } catch (e: Exception) {
                uiState = SignUpUiState(isAuthenticating = false, authErrorMessage = e.localizedMessage)
            }
        }
    }

    fun updateUsername(input: String) {
        uiState = uiState.copy(username = input)
    }

    fun updateEmail(input: String) {
        uiState = uiState.copy(email = input)
    }

    fun updatePassword(input: String) {
        uiState = uiState.copy(password = input)
    }
}


data class SignUpUiState(
    var username: String = "",
    var email: String = "",
    var password: String = "",
    var isAuthenticating: Boolean = false,
    var authErrorMessage: String? = null,
    var authenticationSucceeded: Boolean = false
)

