package com.example.pawtrolapp.android.auth.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.pawtrolapp.android.R
import com.example.pawtrolapp.android.common.theming.ButtonHeight
import com.example.pawtrolapp.android.common.theming.ExtraLargeSpacing
import com.example.pawtrolapp.android.common.theming.LargeSpacing
import com.example.pawtrolapp.android.common.theming.LightColors
import com.example.pawtrolapp.android.common.theming.MediumSpacing
import com.example.pawtrolapp.android.common.theming.PawtrolTheme


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
    onNavigateToSignUp: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Column (
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                color = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.background
                } else {
                    MaterialTheme.colorScheme.surface
                }
            )
            .padding(
                top = ExtraLargeSpacing + LargeSpacing,
                start = LargeSpacing + MediumSpacing,
                end = LargeSpacing + MediumSpacing,
                bottom = LargeSpacing
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = if (isSystemInDarkTheme()) {
                painterResource(R.drawable.pawtrol_w_text_dark_theme)
            } else {
                painterResource(R.drawable.pawtrol_w_text)
            },
            contentDescription = null,
            modifier = Modifier.size(width = 250.dp, height = 250.dp)
        )
        Spacer(modifier = Modifier.height(LargeSpacing))
        Button(
            onClick = {
                onNavigateToLogin()
            },
            modifier = modifier
                .fillMaxWidth()
                .height(ButtonHeight),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = stringResource(id = R.string.login_button_layer))
        }
        Spacer(modifier = Modifier.height(LargeSpacing))
        Button(
            onClick = {
                onNavigateToSignUp()
            },
            modifier = modifier
                .fillMaxWidth()
                .height(ButtonHeight),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp
            ),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = LightColors.secondary
            )
        ) {
            Text(text = stringResource(id = R.string.signup_button_hint))
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
    Column (
        modifier = modifier,
    ) {
        if (screenWidth > 400.dp) {
            Image(
                painter = painterResource(R.drawable.background_elem),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 200.dp, height = 200.dp)
                    .offset {
                        IntOffset(
                            -100, -100
                        )
                    }

            )
            Image(
                painter = painterResource(R.drawable.background_elem),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 300.dp, height = 200.dp)
                    .offset {
                        IntOffset(
                            550, 1500
                        )
                    }

            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    PawtrolTheme {
        HomeScreen(
            onNavigateToLogin = {},
            onNavigateToSignUp = {}
        )
    }
}