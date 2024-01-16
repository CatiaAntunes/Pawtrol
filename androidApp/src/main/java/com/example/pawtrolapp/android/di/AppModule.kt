package com.example.pawtrolapp.android.di

import com.example.pawtrolapp.android.MainActivityViewModel
import com.example.pawtrolapp.android.SharedPreferencesTokenStorage
import com.example.pawtrolapp.android.auth.login.LoginViewModel
import com.example.pawtrolapp.android.auth.signup.SignUpViewModel
import com.example.pawtrolapp.android.createPost.CreatePostViewModel
import com.example.pawtrolapp.android.feed.FeedScreenViewModel
import com.example.pawtrolapp.android.post.PostDetailScreenViewModel
import com.example.pawtrolapp.auth.data.TokenStorage
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get()) }
    single { FirebaseAuth.getInstance() }
    viewModel { SignUpViewModel(get()) }
    viewModel { MainActivityViewModel() }
    viewModel { FeedScreenViewModel() }
    viewModel { PostDetailScreenViewModel() }
    viewModel { CreatePostViewModel()}

    single<TokenStorage> { SharedPreferencesTokenStorage(androidContext()) }


}