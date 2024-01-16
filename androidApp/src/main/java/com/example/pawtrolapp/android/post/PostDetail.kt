package com.example.pawtrolapp.android.post

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination


@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Destination
fun PostDetail(
    postId: String
){
    PostDetailScreen(
        postId = postId
    )
}