package com.example.pawtrolapp.android.common.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.pawtrolapp.android.R
import com.example.pawtrolapp.android.common.theming.isLight

@Composable
fun CircleImage(
    modifier: Modifier,
    url: String
) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape),
        placeholder = if (MaterialTheme.colorScheme.isLight()) {
            painterResource(id = R.drawable.light_image_place_holder)
        } else {
            painterResource(id = R.drawable.dark_image_place_holder)
        },
        contentScale = ContentScale.Crop,
    )
}