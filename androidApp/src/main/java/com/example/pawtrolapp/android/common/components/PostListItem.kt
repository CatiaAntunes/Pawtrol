package com.example.pawtrolapp.android.common.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pawtrolapp.android.R
import com.example.pawtrolapp.android.common.fake_data.Post
import com.example.pawtrolapp.android.common.fake_data.samplePosts
import com.example.pawtrolapp.android.common.theming.DarkGray
import com.example.pawtrolapp.android.common.theming.LargeSpacing
import com.example.pawtrolapp.android.common.theming.LightColors
import com.example.pawtrolapp.android.common.theming.LightGray
import com.example.pawtrolapp.android.common.theming.MediumSpacing
import com.example.pawtrolapp.android.common.theming.PawtrolTheme
import com.example.pawtrolapp.android.common.theming.SmallSpacing
import com.example.pawtrolapp.android.common.theming.isLight
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@RequiresApi(Build.VERSION_CODES.O)
fun formatCreatedAt(createdAt: Long): String {
    val createdAtDateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(createdAt),
        ZoneId.systemDefault()
    )
    val now = LocalDateTime.now()

    val hoursDiff = ChronoUnit.HOURS.between(createdAtDateTime, now)
    return when {
        hoursDiff < 24 -> {
            val minutesDiff = ChronoUnit.MINUTES.between(createdAtDateTime, now)
            val secondsDiff = ChronoUnit.SECONDS.between(createdAtDateTime, now)
            when {
                hoursDiff > 0 -> "$hoursDiff hours ago"
                minutesDiff > 0 -> "$minutesDiff minutes ago"
                else -> "$secondsDiff seconds ago"
            }
        }
        else -> createdAtDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostListItem(
    modifier: Modifier = Modifier,
    post: Post,
    onPostClick: (Post) -> Unit,
    isDetailScreen: Boolean = false
){

    Column (
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .clickable { onPostClick(post) }
    ){
        PostItemHeader(
            name = post.authorName,
            profileUrl = post.authorImage,
            date = formatCreatedAt(post.createdAt.toLong()),
            location = post.location
        )

        AsyncImage(
            model = post.imageURL,
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 1.0f),
            contentScale = ContentScale.Crop,
            placeholder = if (MaterialTheme.colorScheme.isLight()){
                painterResource(id = R.drawable.light_image_place_holder)
            } else {
                painterResource(id = R.drawable.dark_image_place_holder)
            }
        )
        
        PostShareRow(
            shareCount = post.shareCount,
            commentsCount = post.commentCount,
            situation = post.situation
        )

        if (isDetailScreen) {
            Text(
                text = post.text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier.padding(horizontal = LargeSpacing),
                maxLines = 20
            )
            Spacer(modifier = modifier.height(5.dp))
        } else {
            Spacer(modifier = modifier.height(30.dp))
        }

    }
}

@Composable
fun PostItemHeader(
    modifier: Modifier = Modifier,
    name: String,
    profileUrl: String,
    date: String,
    location: String
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = LargeSpacing,
                vertical = MediumSpacing
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
    ){
        CircleImage(
            modifier = modifier.size(20.dp),
            url = profileUrl
        )
        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Box(modifier = modifier
            .size(4.dp)
            .clip(CircleShape)
            .background(
                color = if (MaterialTheme.colorScheme.isLight()) {
                    LightGray
                } else {
                    DarkGray
                }
            )
        )
        
        Text(
            text = date,
            style = MaterialTheme.typography.bodySmall.copy(
                textAlign = TextAlign.Start,
                fontSize = 12.sp,
                color = if (MaterialTheme.colorScheme.isLight()){
                    LightGray
                } else{
                    DarkGray
                }
            ),
            modifier = modifier.weight(1f)
        )

        Text(
            text = location,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

    Icon(
            painter = painterResource(id = R.drawable.location_on_24px),
            contentDescription = null,
            tint = if (MaterialTheme.colorScheme.isLight()){
                LightColors.primary
            } else{
                LightColors.secondary
            }
        )

    }
}

@Composable
fun PostShareRow(
    modifier: Modifier = Modifier,
    shareCount: Int,
    commentsCount: Int,
    situation: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MediumSpacing, horizontal = LargeSpacing),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = situation,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 17.sp),
            color = if (MaterialTheme.colorScheme.isLight()) {
                LightColors.primary
            } else {
                LightColors.secondary
            }
        )
        Spacer(modifier = Modifier.weight(1f)) // This spacer will push the icons to the end
        IconAndText(
            icon = R.drawable.share_24px,
            text = shareCount.toString()
        )
        Spacer(modifier = Modifier.width(LargeSpacing)) // Space between share and comment icons
        IconAndText(
            icon = R.drawable.chat_icon_outlined,
            text = commentsCount.toString()
        )
    }
}

@Composable
fun IconAndText(
    @DrawableRes icon: Int,
    text: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = if (MaterialTheme.colorScheme.isLight()) {
                LightColors.primary
            } else {
                LightColors.secondary
            },
            modifier = Modifier.size(17.dp)
        )
        Spacer(modifier = Modifier.width(10.dp)) // Space between icon and text
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall.copy(fontSize = 17.sp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PostListItemsPreview(){
    PawtrolTheme {
        Surface (color = MaterialTheme.colorScheme.surface){
            PostListItem(
                post = samplePosts.first(),
                onPostClick = {},
                )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PostHeaderPreview() {
    PawtrolTheme {
        Surface {
            PostItemHeader(
                name = "Mr Dip",
                profileUrl = "",
                date = "20 min",
                location = "Birmingham"
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PostShareRowPreview(){
    PawtrolTheme {
        Surface (color = MaterialTheme.colorScheme.surface){
            PostShareRow(
                shareCount = 12,
                commentsCount = 2,
                situation = "Situation"
            )
        }
    }
}