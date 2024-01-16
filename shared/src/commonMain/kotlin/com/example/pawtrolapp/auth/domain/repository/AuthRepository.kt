package com.example.pawtrolapp.auth.domain.repository

import com.example.pawtrolapp.auth.domain.model.AuthResultData
import com.example.pawtrolapp.common.util.Result
internal interface AuthRepository {

    suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Result<AuthResultData>

    suspend fun singIn(
        email: String,
        password: String
    ): Result<AuthResultData>
}

