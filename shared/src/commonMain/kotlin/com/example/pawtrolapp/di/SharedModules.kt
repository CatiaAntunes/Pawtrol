package com.example.pawtrolapp.di

import com.example.pawtrolapp.auth.domain.repository.AuthRepository
import com.example.pawtrolapp.auth.domain.usecase.SignInUseCase
import com.example.pawtrolapp.auth.domain.usecase.SignUpUseCase
import com.example.pawtrolapp.common.util.provideDispatcher
import org.koin.dsl.module

private val authModule = module {

    factory { SignUpUseCase() }
    factory { SignInUseCase() }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}

fun getSharedModules() = listOf(authModule, utilityModule)