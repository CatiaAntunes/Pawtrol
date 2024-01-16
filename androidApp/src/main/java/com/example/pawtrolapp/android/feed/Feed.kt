package com.example.pawtrolapp.android.feed

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.example.pawtrolapp.android.destinations.PostDetailDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Destination(start = true)
@Composable
fun Feed(
    navigator: DestinationsNavigator
) {
    val viewModel: FeedScreenViewModel = koinViewModel()


    FeedScreen(
        postsUiState = viewModel.postsUiState,
        onPostClick = { post ->
            if (post.id.isNotEmpty()) {
                navigator.navigate(PostDetailDestination(postId = post.id))
            } else {
                Log.e(TAG, "Post ID is empty")
            }
        }
    )
}
