package com.example.pawtrolapp.android.feed


import android.content.ContentValues.TAG
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pawtrolapp.android.common.components.PostListItem
import com.example.pawtrolapp.android.common.fake_data.Post
import com.example.pawtrolapp.android.common.fake_data.samplePosts
import com.example.pawtrolapp.android.common.theming.PawtrolTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    postsUiState: PostsUiState,
    onPostClick: (Post) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(items = postsUiState.posts, key = { post -> post.id }) { post ->
                Log.d(TAG, "Post ID: ${post.id}")
                PostListItem(
                    post = post,
                    onPostClick = onPostClick
                )
            }
        }

    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun FeedScreenPreview() {
    PawtrolTheme {
        Surface (color = MaterialTheme.colorScheme.background){
            FeedScreen(
                postsUiState = PostsUiState(posts = samplePosts),
                onPostClick = {}
            )
        }
    }
}
