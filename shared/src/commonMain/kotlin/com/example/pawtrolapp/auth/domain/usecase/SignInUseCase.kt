package com.example.pawtrolapp.auth.domain.usecase

import com.example.pawtrolapp.auth.domain.model.AuthResultData
import com.example.pawtrolapp.auth.domain.repository.AuthRepository
import com.example.pawtrolapp.common.util.Result
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignInUseCase: KoinComponent {
    private val repository: AuthRepository by inject()
    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<AuthResultData>{
        if(email.isBlank() || "@" !in email){
            return Result.Error(
                message = "Invalid email"
            )
        }
        if(password.isBlank() || password.length < 4){
            return Result.Error(
                message = "Invalid password or too short!"
            )
        }

        return repository.singIn(email, password)
    }
}