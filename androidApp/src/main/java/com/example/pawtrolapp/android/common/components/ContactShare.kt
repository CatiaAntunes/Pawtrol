package com.example.pawtrolapp.android.common.components

import android.content.Context
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawtrolapp.android.common.fake_data.Post
import com.example.pawtrolapp.android.common.fake_data.samplePosts
import com.example.pawtrolapp.android.common.theming.DarkColors
import com.example.pawtrolapp.android.common.theming.LargeSpacing
import com.example.pawtrolapp.android.common.theming.LightColors
import com.example.pawtrolapp.android.common.theming.MediumSpacing
import com.example.pawtrolapp.android.common.theming.PawtrolTheme
import com.example.pawtrolapp.android.common.theming.SmallSpacing
import com.example.pawtrolapp.android.common.theming.isLight

@Composable
fun ContactShareRow(
    modifier: Modifier = Modifier,
    post: Post,
){
    val context = LocalContext.current
    val phoneNumber = post.contactInfo

    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 0.dp,
                horizontal = LargeSpacing
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Row(
            modifier = modifier
                .padding(
                    vertical = MediumSpacing,
                    horizontal = SmallSpacing
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {

            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = null,
                tint = if (MaterialTheme.colorScheme.isLight()){
                    LightColors.primary
                } else {
                    LightColors.secondary
                },
                modifier = modifier
                    .size(15.dp)
                    .clickable { dialPhoneNumber(context, phoneNumber) }
            )
            Spacer(modifier = modifier.width(9.dp).clickable { dialPhoneNumber(context, phoneNumber) })
            Text(
                text = post.contactInfo,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 15.sp
                ),
                textAlign = TextAlign.Start,
                color = if (MaterialTheme.colorScheme.isLight()){
                    LightColors.onSurface
                } else {
                    DarkColors.onSurface
                },
                modifier = modifier.clickable { dialPhoneNumber(context, phoneNumber) }
            )

            Spacer(modifier = modifier.width(20.dp))


            Text(
                text = "Talk about it!",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 15.sp
                ),
                color = if (MaterialTheme.colorScheme.isLight()){
                    LightColors.onSurface
                } else {
                    DarkColors.onSurface
                },
                modifier = modifier.clickable { sharePost(context, post) }
            )
            Spacer(modifier = modifier.width(10.dp).clickable { sharePost(context, post) })
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null,
                tint = if (MaterialTheme.colorScheme.isLight()){
                    LightColors.primary
                } else {
                    LightColors.secondary
                },
                modifier = modifier.size(15.dp).clickable { sharePost(context, post) }
            )
        }

    }
}

private fun dialPhoneNumber(context: Context, phoneNumber: String){
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phoneNumber")
    context.startActivity(intent)
}

private fun sharePost(context: Context, post: Post){
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, post.text)
    context.startActivity(Intent.createChooser(shareIntent, "Share Post"))
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ContactShareRowPreview(){
    PawtrolTheme {
        Surface (color = MaterialTheme.colorScheme.surface){
            ContactShareRow(
                post = samplePosts.first()
            )
        }
    }
}